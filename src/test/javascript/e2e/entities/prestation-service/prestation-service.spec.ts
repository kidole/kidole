import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PrestationServiceComponentsPage,
  PrestationServiceDeleteDialog,
  PrestationServiceUpdatePage
} from './prestation-service.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PrestationService e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let prestationServiceComponentsPage: PrestationServiceComponentsPage;
  let prestationServiceUpdatePage: PrestationServiceUpdatePage;
  let prestationServiceDeleteDialog: PrestationServiceDeleteDialog;
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

  it('should load PrestationServices', async () => {
    await navBarPage.goToEntity('prestation-service');
    prestationServiceComponentsPage = new PrestationServiceComponentsPage();
    await browser.wait(ec.visibilityOf(prestationServiceComponentsPage.title), 5000);
    expect(await prestationServiceComponentsPage.getTitle()).to.eq('kidoleApp.prestationService.home.title');
  });

  it('should load create PrestationService page', async () => {
    await prestationServiceComponentsPage.clickOnCreateButton();
    prestationServiceUpdatePage = new PrestationServiceUpdatePage();
    expect(await prestationServiceUpdatePage.getPageTitle()).to.eq('kidoleApp.prestationService.home.createOrEditLabel');
    await prestationServiceUpdatePage.cancel();
  });

  it('should create and save PrestationServices', async () => {
    const nbButtonsBeforeCreate = await prestationServiceComponentsPage.countDeleteButtons();

    await prestationServiceComponentsPage.clickOnCreateButton();
    await promise.all([
      prestationServiceUpdatePage.setPrestationServiceNameInput('prestationServiceName'),
      prestationServiceUpdatePage.prestationServiceNameStateSelectLastOption(),
      prestationServiceUpdatePage.setPrestationServiceNameDetailInput('prestationServiceNameDetail'),
      prestationServiceUpdatePage.setPrestationServiceNameImageInput(absolutePath),
      prestationServiceUpdatePage.rubriqueSelectLastOption(),
      prestationServiceUpdatePage.userSelectLastOption()
    ]);
    expect(await prestationServiceUpdatePage.getPrestationServiceNameInput()).to.eq(
      'prestationServiceName',
      'Expected PrestationServiceName value to be equals to prestationServiceName'
    );
    expect(await prestationServiceUpdatePage.getPrestationServiceNameDetailInput()).to.eq(
      'prestationServiceNameDetail',
      'Expected PrestationServiceNameDetail value to be equals to prestationServiceNameDetail'
    );
    expect(await prestationServiceUpdatePage.getPrestationServiceNameImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected PrestationServiceNameImage value to be end with ' + fileNameToUpload
    );
    await prestationServiceUpdatePage.save();
    expect(await prestationServiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await prestationServiceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PrestationService', async () => {
    const nbButtonsBeforeDelete = await prestationServiceComponentsPage.countDeleteButtons();
    await prestationServiceComponentsPage.clickOnLastDeleteButton();

    prestationServiceDeleteDialog = new PrestationServiceDeleteDialog();
    expect(await prestationServiceDeleteDialog.getDialogTitle()).to.eq('kidoleApp.prestationService.delete.question');
    await prestationServiceDeleteDialog.clickOnConfirmButton();

    expect(await prestationServiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
