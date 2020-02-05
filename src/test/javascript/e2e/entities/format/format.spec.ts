import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormatComponentsPage, FormatDeleteDialog, FormatUpdatePage } from './format.page-object';

const expect = chai.expect;

describe('Format e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formatComponentsPage: FormatComponentsPage;
  let formatUpdatePage: FormatUpdatePage;
  let formatDeleteDialog: FormatDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Formats', async () => {
    await navBarPage.goToEntity('format');
    formatComponentsPage = new FormatComponentsPage();
    await browser.wait(ec.visibilityOf(formatComponentsPage.title), 5000);
    expect(await formatComponentsPage.getTitle()).to.eq('kidoleApp.format.home.title');
  });

  it('should load create Format page', async () => {
    await formatComponentsPage.clickOnCreateButton();
    formatUpdatePage = new FormatUpdatePage();
    expect(await formatUpdatePage.getPageTitle()).to.eq('kidoleApp.format.home.createOrEditLabel');
    await formatUpdatePage.cancel();
  });

  it('should create and save Formats', async () => {
    const nbButtonsBeforeCreate = await formatComponentsPage.countDeleteButtons();

    await formatComponentsPage.clickOnCreateButton();
    await promise.all([
      formatUpdatePage.setFormatNameInput('formatName'),
      formatUpdatePage.setWinerQtyInput('5'),
      formatUpdatePage.phaseSelectLastOption(),
      formatUpdatePage.competitionSelectLastOption()
    ]);
    expect(await formatUpdatePage.getFormatNameInput()).to.eq('formatName', 'Expected FormatName value to be equals to formatName');
    expect(await formatUpdatePage.getWinerQtyInput()).to.eq('5', 'Expected winerQty value to be equals to 5');
    await formatUpdatePage.save();
    expect(await formatUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Format', async () => {
    const nbButtonsBeforeDelete = await formatComponentsPage.countDeleteButtons();
    await formatComponentsPage.clickOnLastDeleteButton();

    formatDeleteDialog = new FormatDeleteDialog();
    expect(await formatDeleteDialog.getDialogTitle()).to.eq('kidoleApp.format.delete.question');
    await formatDeleteDialog.clickOnConfirmButton();

    expect(await formatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
