import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PoulesComponentsPage, PoulesDeleteDialog, PoulesUpdatePage } from './poules.page-object';

const expect = chai.expect;

describe('Poules e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let poulesComponentsPage: PoulesComponentsPage;
  let poulesUpdatePage: PoulesUpdatePage;
  let poulesDeleteDialog: PoulesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Poules', async () => {
    await navBarPage.goToEntity('poules');
    poulesComponentsPage = new PoulesComponentsPage();
    await browser.wait(ec.visibilityOf(poulesComponentsPage.title), 5000);
    expect(await poulesComponentsPage.getTitle()).to.eq('kidoleApp.poules.home.title');
  });

  it('should load create Poules page', async () => {
    await poulesComponentsPage.clickOnCreateButton();
    poulesUpdatePage = new PoulesUpdatePage();
    expect(await poulesUpdatePage.getPageTitle()).to.eq('kidoleApp.poules.home.createOrEditLabel');
    await poulesUpdatePage.cancel();
  });

  it('should create and save Poules', async () => {
    const nbButtonsBeforeCreate = await poulesComponentsPage.countDeleteButtons();

    await poulesComponentsPage.clickOnCreateButton();
    await promise.all([poulesUpdatePage.setPoulesNameInput('poulesName')]);
    expect(await poulesUpdatePage.getPoulesNameInput()).to.eq('poulesName', 'Expected PoulesName value to be equals to poulesName');
    await poulesUpdatePage.save();
    expect(await poulesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await poulesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Poules', async () => {
    const nbButtonsBeforeDelete = await poulesComponentsPage.countDeleteButtons();
    await poulesComponentsPage.clickOnLastDeleteButton();

    poulesDeleteDialog = new PoulesDeleteDialog();
    expect(await poulesDeleteDialog.getDialogTitle()).to.eq('kidoleApp.poules.delete.question');
    await poulesDeleteDialog.clickOnConfirmButton();

    expect(await poulesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
