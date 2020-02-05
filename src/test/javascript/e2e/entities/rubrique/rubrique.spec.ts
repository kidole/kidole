import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RubriqueComponentsPage, RubriqueDeleteDialog, RubriqueUpdatePage } from './rubrique.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Rubrique e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rubriqueComponentsPage: RubriqueComponentsPage;
  let rubriqueUpdatePage: RubriqueUpdatePage;
  let rubriqueDeleteDialog: RubriqueDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Rubriques', async () => {
    await navBarPage.goToEntity('rubrique');
    rubriqueComponentsPage = new RubriqueComponentsPage();
    await browser.wait(ec.visibilityOf(rubriqueComponentsPage.title), 5000);
    expect(await rubriqueComponentsPage.getTitle()).to.eq('kidoleApp.rubrique.home.title');
  });

  it('should load create Rubrique page', async () => {
    await rubriqueComponentsPage.clickOnCreateButton();
    rubriqueUpdatePage = new RubriqueUpdatePage();
    expect(await rubriqueUpdatePage.getPageTitle()).to.eq('kidoleApp.rubrique.home.createOrEditLabel');
    await rubriqueUpdatePage.cancel();
  });

  it('should create and save Rubriques', async () => {
    const nbButtonsBeforeCreate = await rubriqueComponentsPage.countDeleteButtons();

    await rubriqueComponentsPage.clickOnCreateButton();
    await promise.all([
      rubriqueUpdatePage.setRubriqueNameInput('rubriqueName'),
      rubriqueUpdatePage.setRubriqueDetailsInput('rubriqueDetails'),
      rubriqueUpdatePage.setRubriqueImageInput(absolutePath)
    ]);
    expect(await rubriqueUpdatePage.getRubriqueNameInput()).to.eq(
      'rubriqueName',
      'Expected RubriqueName value to be equals to rubriqueName'
    );
    expect(await rubriqueUpdatePage.getRubriqueDetailsInput()).to.eq(
      'rubriqueDetails',
      'Expected RubriqueDetails value to be equals to rubriqueDetails'
    );
    expect(await rubriqueUpdatePage.getRubriqueImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected RubriqueImage value to be end with ' + fileNameToUpload
    );
    await rubriqueUpdatePage.save();
    expect(await rubriqueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await rubriqueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Rubrique', async () => {
    const nbButtonsBeforeDelete = await rubriqueComponentsPage.countDeleteButtons();
    await rubriqueComponentsPage.clickOnLastDeleteButton();

    rubriqueDeleteDialog = new RubriqueDeleteDialog();
    expect(await rubriqueDeleteDialog.getDialogTitle()).to.eq('kidoleApp.rubrique.delete.question');
    await rubriqueDeleteDialog.clickOnConfirmButton();

    expect(await rubriqueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
