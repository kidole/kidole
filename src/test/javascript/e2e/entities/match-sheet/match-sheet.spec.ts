import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MatchSheetComponentsPage, MatchSheetDeleteDialog, MatchSheetUpdatePage } from './match-sheet.page-object';

const expect = chai.expect;

describe('MatchSheet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let matchSheetComponentsPage: MatchSheetComponentsPage;
  let matchSheetUpdatePage: MatchSheetUpdatePage;
  let matchSheetDeleteDialog: MatchSheetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MatchSheets', async () => {
    await navBarPage.goToEntity('match-sheet');
    matchSheetComponentsPage = new MatchSheetComponentsPage();
    await browser.wait(ec.visibilityOf(matchSheetComponentsPage.title), 5000);
    expect(await matchSheetComponentsPage.getTitle()).to.eq('kidoleApp.matchSheet.home.title');
  });

  it('should load create MatchSheet page', async () => {
    await matchSheetComponentsPage.clickOnCreateButton();
    matchSheetUpdatePage = new MatchSheetUpdatePage();
    expect(await matchSheetUpdatePage.getPageTitle()).to.eq('kidoleApp.matchSheet.home.createOrEditLabel');
    await matchSheetUpdatePage.cancel();
  });

  it('should create and save MatchSheets', async () => {
    const nbButtonsBeforeCreate = await matchSheetComponentsPage.countDeleteButtons();

    await matchSheetComponentsPage.clickOnCreateButton();
    await promise.all([
      matchSheetUpdatePage.setMatchSheetNameInput('matchSheetName'),
      matchSheetUpdatePage.setMatchSheetResumeInput('matchSheetResume')
    ]);
    expect(await matchSheetUpdatePage.getMatchSheetNameInput()).to.eq(
      'matchSheetName',
      'Expected MatchSheetName value to be equals to matchSheetName'
    );
    expect(await matchSheetUpdatePage.getMatchSheetResumeInput()).to.eq(
      'matchSheetResume',
      'Expected MatchSheetResume value to be equals to matchSheetResume'
    );
    const selectedIsfirst = matchSheetUpdatePage.getIsfirstInput();
    if (await selectedIsfirst.isSelected()) {
      await matchSheetUpdatePage.getIsfirstInput().click();
      expect(await matchSheetUpdatePage.getIsfirstInput().isSelected(), 'Expected isfirst not to be selected').to.be.false;
    } else {
      await matchSheetUpdatePage.getIsfirstInput().click();
      expect(await matchSheetUpdatePage.getIsfirstInput().isSelected(), 'Expected isfirst to be selected').to.be.true;
    }
    await matchSheetUpdatePage.save();
    expect(await matchSheetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await matchSheetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MatchSheet', async () => {
    const nbButtonsBeforeDelete = await matchSheetComponentsPage.countDeleteButtons();
    await matchSheetComponentsPage.clickOnLastDeleteButton();

    matchSheetDeleteDialog = new MatchSheetDeleteDialog();
    expect(await matchSheetDeleteDialog.getDialogTitle()).to.eq('kidoleApp.matchSheet.delete.question');
    await matchSheetDeleteDialog.clickOnConfirmButton();

    expect(await matchSheetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
