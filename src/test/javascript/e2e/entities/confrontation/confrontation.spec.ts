import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ConfrontationComponentsPage, ConfrontationDeleteDialog, ConfrontationUpdatePage } from './confrontation.page-object';

const expect = chai.expect;

describe('Confrontation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let confrontationComponentsPage: ConfrontationComponentsPage;
  let confrontationUpdatePage: ConfrontationUpdatePage;
  let confrontationDeleteDialog: ConfrontationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Confrontations', async () => {
    await navBarPage.goToEntity('confrontation');
    confrontationComponentsPage = new ConfrontationComponentsPage();
    await browser.wait(ec.visibilityOf(confrontationComponentsPage.title), 5000);
    expect(await confrontationComponentsPage.getTitle()).to.eq('kidoleApp.confrontation.home.title');
  });

  it('should load create Confrontation page', async () => {
    await confrontationComponentsPage.clickOnCreateButton();
    confrontationUpdatePage = new ConfrontationUpdatePage();
    expect(await confrontationUpdatePage.getPageTitle()).to.eq('kidoleApp.confrontation.home.createOrEditLabel');
    await confrontationUpdatePage.cancel();
  });

  it('should create and save Confrontations', async () => {
    const nbButtonsBeforeCreate = await confrontationComponentsPage.countDeleteButtons();

    await confrontationComponentsPage.clickOnCreateButton();
    await promise.all([
      confrontationUpdatePage.setConfrontationNameInput('confrontationName'),
      confrontationUpdatePage.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      confrontationUpdatePage.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      confrontationUpdatePage.setConfrontationDetailsInput('confrontationDetails'),
      confrontationUpdatePage.matchsheetSelectLastOption(),
      confrontationUpdatePage.localisationSelectLastOption(),
      confrontationUpdatePage.journeeSelectLastOption()
    ]);
    expect(await confrontationUpdatePage.getConfrontationNameInput()).to.eq(
      'confrontationName',
      'Expected ConfrontationName value to be equals to confrontationName'
    );
    expect(await confrontationUpdatePage.getStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected startDate value to be equals to 2000-12-31'
    );
    expect(await confrontationUpdatePage.getEndDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected endDate value to be equals to 2000-12-31'
    );
    expect(await confrontationUpdatePage.getConfrontationDetailsInput()).to.eq(
      'confrontationDetails',
      'Expected ConfrontationDetails value to be equals to confrontationDetails'
    );
    await confrontationUpdatePage.save();
    expect(await confrontationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await confrontationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Confrontation', async () => {
    const nbButtonsBeforeDelete = await confrontationComponentsPage.countDeleteButtons();
    await confrontationComponentsPage.clickOnLastDeleteButton();

    confrontationDeleteDialog = new ConfrontationDeleteDialog();
    expect(await confrontationDeleteDialog.getDialogTitle()).to.eq('kidoleApp.confrontation.delete.question');
    await confrontationDeleteDialog.clickOnConfirmButton();

    expect(await confrontationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
