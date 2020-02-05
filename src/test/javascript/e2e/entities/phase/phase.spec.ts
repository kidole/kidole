import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PhaseComponentsPage, PhaseDeleteDialog, PhaseUpdatePage } from './phase.page-object';

const expect = chai.expect;

describe('Phase e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let phaseComponentsPage: PhaseComponentsPage;
  let phaseUpdatePage: PhaseUpdatePage;
  let phaseDeleteDialog: PhaseDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Phases', async () => {
    await navBarPage.goToEntity('phase');
    phaseComponentsPage = new PhaseComponentsPage();
    await browser.wait(ec.visibilityOf(phaseComponentsPage.title), 5000);
    expect(await phaseComponentsPage.getTitle()).to.eq('kidoleApp.phase.home.title');
  });

  it('should load create Phase page', async () => {
    await phaseComponentsPage.clickOnCreateButton();
    phaseUpdatePage = new PhaseUpdatePage();
    expect(await phaseUpdatePage.getPageTitle()).to.eq('kidoleApp.phase.home.createOrEditLabel');
    await phaseUpdatePage.cancel();
  });

  it('should create and save Phases', async () => {
    const nbButtonsBeforeCreate = await phaseComponentsPage.countDeleteButtons();

    await phaseComponentsPage.clickOnCreateButton();
    await promise.all([
      phaseUpdatePage.setPhaseNameInput('phaseName'),
      phaseUpdatePage.setPhaseNumberInput('5'),
      phaseUpdatePage.setPhaseDayNumberInput('5')
    ]);
    expect(await phaseUpdatePage.getPhaseNameInput()).to.eq('phaseName', 'Expected PhaseName value to be equals to phaseName');
    expect(await phaseUpdatePage.getPhaseNumberInput()).to.eq('5', 'Expected phaseNumber value to be equals to 5');
    expect(await phaseUpdatePage.getPhaseDayNumberInput()).to.eq('5', 'Expected phaseDayNumber value to be equals to 5');
    await phaseUpdatePage.save();
    expect(await phaseUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await phaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Phase', async () => {
    const nbButtonsBeforeDelete = await phaseComponentsPage.countDeleteButtons();
    await phaseComponentsPage.clickOnLastDeleteButton();

    phaseDeleteDialog = new PhaseDeleteDialog();
    expect(await phaseDeleteDialog.getDialogTitle()).to.eq('kidoleApp.phase.delete.question');
    await phaseDeleteDialog.clickOnConfirmButton();

    expect(await phaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
