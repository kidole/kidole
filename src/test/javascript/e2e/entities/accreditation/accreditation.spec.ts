import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AccreditationComponentsPage, AccreditationDeleteDialog, AccreditationUpdatePage } from './accreditation.page-object';

const expect = chai.expect;

describe('Accreditation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let accreditationComponentsPage: AccreditationComponentsPage;
  let accreditationUpdatePage: AccreditationUpdatePage;
  let accreditationDeleteDialog: AccreditationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Accreditations', async () => {
    await navBarPage.goToEntity('accreditation');
    accreditationComponentsPage = new AccreditationComponentsPage();
    await browser.wait(ec.visibilityOf(accreditationComponentsPage.title), 5000);
    expect(await accreditationComponentsPage.getTitle()).to.eq('kidoleApp.accreditation.home.title');
  });

  it('should load create Accreditation page', async () => {
    await accreditationComponentsPage.clickOnCreateButton();
    accreditationUpdatePage = new AccreditationUpdatePage();
    expect(await accreditationUpdatePage.getPageTitle()).to.eq('kidoleApp.accreditation.home.createOrEditLabel');
    await accreditationUpdatePage.cancel();
  });

  it('should create and save Accreditations', async () => {
    const nbButtonsBeforeCreate = await accreditationComponentsPage.countDeleteButtons();

    await accreditationComponentsPage.clickOnCreateButton();
    await promise.all([
      accreditationUpdatePage.accreditationNameSelectLastOption(),
      accreditationUpdatePage.setFirstNameInput('firstName'),
      accreditationUpdatePage.setLastNameInput('lastName'),
      accreditationUpdatePage.setAccreditationEmailInput('E@V)z.2.5(H,'),
      accreditationUpdatePage.accreditationStatusSelectLastOption(),
      accreditationUpdatePage.setAccreditationDetailInput('accreditationDetail'),
      accreditationUpdatePage.competitionSelectLastOption()
    ]);
    expect(await accreditationUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await accreditationUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await accreditationUpdatePage.getAccreditationEmailInput()).to.eq(
      'E@V)z.2.5(H,',
      'Expected AccreditationEmail value to be equals to E@V)z.2.5(H,'
    );
    expect(await accreditationUpdatePage.getAccreditationDetailInput()).to.eq(
      'accreditationDetail',
      'Expected AccreditationDetail value to be equals to accreditationDetail'
    );
    await accreditationUpdatePage.save();
    expect(await accreditationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await accreditationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Accreditation', async () => {
    const nbButtonsBeforeDelete = await accreditationComponentsPage.countDeleteButtons();
    await accreditationComponentsPage.clickOnLastDeleteButton();

    accreditationDeleteDialog = new AccreditationDeleteDialog();
    expect(await accreditationDeleteDialog.getDialogTitle()).to.eq('kidoleApp.accreditation.delete.question');
    await accreditationDeleteDialog.clickOnConfirmButton();

    expect(await accreditationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
