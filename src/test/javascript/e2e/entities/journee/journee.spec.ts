import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JourneeComponentsPage, JourneeDeleteDialog, JourneeUpdatePage } from './journee.page-object';

const expect = chai.expect;

describe('Journee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let journeeComponentsPage: JourneeComponentsPage;
  let journeeUpdatePage: JourneeUpdatePage;
  let journeeDeleteDialog: JourneeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Journees', async () => {
    await navBarPage.goToEntity('journee');
    journeeComponentsPage = new JourneeComponentsPage();
    await browser.wait(ec.visibilityOf(journeeComponentsPage.title), 5000);
    expect(await journeeComponentsPage.getTitle()).to.eq('kidoleApp.journee.home.title');
  });

  it('should load create Journee page', async () => {
    await journeeComponentsPage.clickOnCreateButton();
    journeeUpdatePage = new JourneeUpdatePage();
    expect(await journeeUpdatePage.getPageTitle()).to.eq('kidoleApp.journee.home.createOrEditLabel');
    await journeeUpdatePage.cancel();
  });

  it('should create and save Journees', async () => {
    const nbButtonsBeforeCreate = await journeeComponentsPage.countDeleteButtons();

    await journeeComponentsPage.clickOnCreateButton();
    await promise.all([journeeUpdatePage.setJourneeNameInput('journeeName'), journeeUpdatePage.phaseSelectLastOption()]);
    expect(await journeeUpdatePage.getJourneeNameInput()).to.eq('journeeName', 'Expected JourneeName value to be equals to journeeName');
    await journeeUpdatePage.save();
    expect(await journeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await journeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Journee', async () => {
    const nbButtonsBeforeDelete = await journeeComponentsPage.countDeleteButtons();
    await journeeComponentsPage.clickOnLastDeleteButton();

    journeeDeleteDialog = new JourneeDeleteDialog();
    expect(await journeeDeleteDialog.getDialogTitle()).to.eq('kidoleApp.journee.delete.question');
    await journeeDeleteDialog.clickOnConfirmButton();

    expect(await journeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
