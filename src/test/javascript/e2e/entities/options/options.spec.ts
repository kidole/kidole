import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OptionsComponentsPage, OptionsDeleteDialog, OptionsUpdatePage } from './options.page-object';

const expect = chai.expect;

describe('Options e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let optionsComponentsPage: OptionsComponentsPage;
  let optionsUpdatePage: OptionsUpdatePage;
  let optionsDeleteDialog: OptionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Options', async () => {
    await navBarPage.goToEntity('options');
    optionsComponentsPage = new OptionsComponentsPage();
    await browser.wait(ec.visibilityOf(optionsComponentsPage.title), 5000);
    expect(await optionsComponentsPage.getTitle()).to.eq('kidoleApp.options.home.title');
  });

  it('should load create Options page', async () => {
    await optionsComponentsPage.clickOnCreateButton();
    optionsUpdatePage = new OptionsUpdatePage();
    expect(await optionsUpdatePage.getPageTitle()).to.eq('kidoleApp.options.home.createOrEditLabel');
    await optionsUpdatePage.cancel();
  });

  it('should create and save Options', async () => {
    const nbButtonsBeforeCreate = await optionsComponentsPage.countDeleteButtons();

    await optionsComponentsPage.clickOnCreateButton();
    await promise.all([
      optionsUpdatePage.setOptionsNameInput('optionsName'),
      optionsUpdatePage.setOptionsValue2Input('5'),
      optionsUpdatePage.competitionSelectLastOption()
    ]);
    expect(await optionsUpdatePage.getOptionsNameInput()).to.eq('optionsName', 'Expected OptionsName value to be equals to optionsName');
    const selectedOptionsValue1 = optionsUpdatePage.getOptionsValue1Input();
    if (await selectedOptionsValue1.isSelected()) {
      await optionsUpdatePage.getOptionsValue1Input().click();
      expect(await optionsUpdatePage.getOptionsValue1Input().isSelected(), 'Expected optionsValue1 not to be selected').to.be.false;
    } else {
      await optionsUpdatePage.getOptionsValue1Input().click();
      expect(await optionsUpdatePage.getOptionsValue1Input().isSelected(), 'Expected optionsValue1 to be selected').to.be.true;
    }
    expect(await optionsUpdatePage.getOptionsValue2Input()).to.eq('5', 'Expected optionsValue2 value to be equals to 5');
    await optionsUpdatePage.save();
    expect(await optionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await optionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Options', async () => {
    const nbButtonsBeforeDelete = await optionsComponentsPage.countDeleteButtons();
    await optionsComponentsPage.clickOnLastDeleteButton();

    optionsDeleteDialog = new OptionsDeleteDialog();
    expect(await optionsDeleteDialog.getDialogTitle()).to.eq('kidoleApp.options.delete.question');
    await optionsDeleteDialog.clickOnConfirmButton();

    expect(await optionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
