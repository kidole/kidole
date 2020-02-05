import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DelegationMembersComponentsPage,
  DelegationMembersDeleteDialog,
  DelegationMembersUpdatePage
} from './delegation-members.page-object';

const expect = chai.expect;

describe('DelegationMembers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let delegationMembersComponentsPage: DelegationMembersComponentsPage;
  let delegationMembersUpdatePage: DelegationMembersUpdatePage;
  let delegationMembersDeleteDialog: DelegationMembersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DelegationMembers', async () => {
    await navBarPage.goToEntity('delegation-members');
    delegationMembersComponentsPage = new DelegationMembersComponentsPage();
    await browser.wait(ec.visibilityOf(delegationMembersComponentsPage.title), 5000);
    expect(await delegationMembersComponentsPage.getTitle()).to.eq('kidoleApp.delegationMembers.home.title');
  });

  it('should load create DelegationMembers page', async () => {
    await delegationMembersComponentsPage.clickOnCreateButton();
    delegationMembersUpdatePage = new DelegationMembersUpdatePage();
    expect(await delegationMembersUpdatePage.getPageTitle()).to.eq('kidoleApp.delegationMembers.home.createOrEditLabel');
    await delegationMembersUpdatePage.cancel();
  });

  it('should create and save DelegationMembers', async () => {
    const nbButtonsBeforeCreate = await delegationMembersComponentsPage.countDeleteButtons();

    await delegationMembersComponentsPage.clickOnCreateButton();
    await promise.all([
      delegationMembersUpdatePage.setDelegationMembersStateInput('delegationMembersState'),
      delegationMembersUpdatePage.setDelegationMembersCodeInput('delegationMembersCode'),
      delegationMembersUpdatePage.setDelegationMembersDetailInput('delegationMembersDetail'),
      delegationMembersUpdatePage.userSelectLastOption()
    ]);
    expect(await delegationMembersUpdatePage.getDelegationMembersStateInput()).to.eq(
      'delegationMembersState',
      'Expected DelegationMembersState value to be equals to delegationMembersState'
    );
    expect(await delegationMembersUpdatePage.getDelegationMembersCodeInput()).to.eq(
      'delegationMembersCode',
      'Expected DelegationMembersCode value to be equals to delegationMembersCode'
    );
    expect(await delegationMembersUpdatePage.getDelegationMembersDetailInput()).to.eq(
      'delegationMembersDetail',
      'Expected DelegationMembersDetail value to be equals to delegationMembersDetail'
    );
    await delegationMembersUpdatePage.save();
    expect(await delegationMembersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await delegationMembersComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DelegationMembers', async () => {
    const nbButtonsBeforeDelete = await delegationMembersComponentsPage.countDeleteButtons();
    await delegationMembersComponentsPage.clickOnLastDeleteButton();

    delegationMembersDeleteDialog = new DelegationMembersDeleteDialog();
    expect(await delegationMembersDeleteDialog.getDialogTitle()).to.eq('kidoleApp.delegationMembers.delete.question');
    await delegationMembersDeleteDialog.clickOnConfirmButton();

    expect(await delegationMembersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
