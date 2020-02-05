import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoryComponentsPage, CategoryDeleteDialog, CategoryUpdatePage } from './category.page-object';

const expect = chai.expect;

describe('Category e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoryComponentsPage: CategoryComponentsPage;
  let categoryUpdatePage: CategoryUpdatePage;
  let categoryDeleteDialog: CategoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Categories', async () => {
    await navBarPage.goToEntity('category');
    categoryComponentsPage = new CategoryComponentsPage();
    await browser.wait(ec.visibilityOf(categoryComponentsPage.title), 5000);
    expect(await categoryComponentsPage.getTitle()).to.eq('kidoleApp.category.home.title');
  });

  it('should load create Category page', async () => {
    await categoryComponentsPage.clickOnCreateButton();
    categoryUpdatePage = new CategoryUpdatePage();
    expect(await categoryUpdatePage.getPageTitle()).to.eq('kidoleApp.category.home.createOrEditLabel');
    await categoryUpdatePage.cancel();
  });

  it('should create and save Categories', async () => {
    const nbButtonsBeforeCreate = await categoryComponentsPage.countDeleteButtons();

    await categoryComponentsPage.clickOnCreateButton();
    await promise.all([
      categoryUpdatePage.setCategoryNameInput('categoryName'),
      categoryUpdatePage.setYearlimitInput('5'),
      categoryUpdatePage.setTeamLimitNumbInput('5'),
      categoryUpdatePage.setParticipantLimitByteamInput('5'),
      categoryUpdatePage.setCategoryRuleInput('categoryRule'),
      categoryUpdatePage.disciplineSelectLastOption()
    ]);
    expect(await categoryUpdatePage.getCategoryNameInput()).to.eq(
      'categoryName',
      'Expected CategoryName value to be equals to categoryName'
    );
    expect(await categoryUpdatePage.getYearlimitInput()).to.eq('5', 'Expected yearlimit value to be equals to 5');
    expect(await categoryUpdatePage.getTeamLimitNumbInput()).to.eq('5', 'Expected teamLimitNumb value to be equals to 5');
    expect(await categoryUpdatePage.getParticipantLimitByteamInput()).to.eq('5', 'Expected participantLimitByteam value to be equals to 5');
    expect(await categoryUpdatePage.getCategoryRuleInput()).to.eq(
      'categoryRule',
      'Expected CategoryRule value to be equals to categoryRule'
    );
    await categoryUpdatePage.save();
    expect(await categoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await categoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Category', async () => {
    const nbButtonsBeforeDelete = await categoryComponentsPage.countDeleteButtons();
    await categoryComponentsPage.clickOnLastDeleteButton();

    categoryDeleteDialog = new CategoryDeleteDialog();
    expect(await categoryDeleteDialog.getDialogTitle()).to.eq('kidoleApp.category.delete.question');
    await categoryDeleteDialog.clickOnConfirmButton();

    expect(await categoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
