import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DelegationComponentsPage, DelegationDeleteDialog, DelegationUpdatePage } from './delegation.page-object';

const expect = chai.expect;

describe('Delegation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let delegationComponentsPage: DelegationComponentsPage;
  let delegationUpdatePage: DelegationUpdatePage;
  let delegationDeleteDialog: DelegationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Delegations', async () => {
    await navBarPage.goToEntity('delegation');
    delegationComponentsPage = new DelegationComponentsPage();
    await browser.wait(ec.visibilityOf(delegationComponentsPage.title), 5000);
    expect(await delegationComponentsPage.getTitle()).to.eq('kidoleApp.delegation.home.title');
  });

  it('should load create Delegation page', async () => {
    await delegationComponentsPage.clickOnCreateButton();
    delegationUpdatePage = new DelegationUpdatePage();
    expect(await delegationUpdatePage.getPageTitle()).to.eq('kidoleApp.delegation.home.createOrEditLabel');
    await delegationUpdatePage.cancel();
  });

  it('should create and save Delegations', async () => {
    const nbButtonsBeforeCreate = await delegationComponentsPage.countDeleteButtons();

    await delegationComponentsPage.clickOnCreateButton();
    await promise.all([
      delegationUpdatePage.setDelegationNameInput('delegationName'),
      delegationUpdatePage.setDelegationCountryInput('delegationCountry'),
      delegationUpdatePage.setDelegationLocalityInput('delegationLocality'),
      delegationUpdatePage.setDelegationCodeInput('delegationCode'),
      delegationUpdatePage.delegateMemberSelectLastOption()
    ]);
    expect(await delegationUpdatePage.getDelegationNameInput()).to.eq(
      'delegationName',
      'Expected DelegationName value to be equals to delegationName'
    );
    expect(await delegationUpdatePage.getDelegationCountryInput()).to.eq(
      'delegationCountry',
      'Expected DelegationCountry value to be equals to delegationCountry'
    );
    expect(await delegationUpdatePage.getDelegationLocalityInput()).to.eq(
      'delegationLocality',
      'Expected DelegationLocality value to be equals to delegationLocality'
    );
    expect(await delegationUpdatePage.getDelegationCodeInput()).to.eq(
      'delegationCode',
      'Expected DelegationCode value to be equals to delegationCode'
    );
    await delegationUpdatePage.save();
    expect(await delegationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await delegationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Delegation', async () => {
    const nbButtonsBeforeDelete = await delegationComponentsPage.countDeleteButtons();
    await delegationComponentsPage.clickOnLastDeleteButton();

    delegationDeleteDialog = new DelegationDeleteDialog();
    expect(await delegationDeleteDialog.getDialogTitle()).to.eq('kidoleApp.delegation.delete.question');
    await delegationDeleteDialog.clickOnConfirmButton();

    expect(await delegationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
