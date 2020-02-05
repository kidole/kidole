import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CompetitionComponentsPage, CompetitionDeleteDialog, CompetitionUpdatePage } from './competition.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Competition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let competitionComponentsPage: CompetitionComponentsPage;
  let competitionUpdatePage: CompetitionUpdatePage;
  let competitionDeleteDialog: CompetitionDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Competitions', async () => {
    await navBarPage.goToEntity('competition');
    competitionComponentsPage = new CompetitionComponentsPage();
    await browser.wait(ec.visibilityOf(competitionComponentsPage.title), 5000);
    expect(await competitionComponentsPage.getTitle()).to.eq('kidoleApp.competition.home.title');
  });

  it('should load create Competition page', async () => {
    await competitionComponentsPage.clickOnCreateButton();
    competitionUpdatePage = new CompetitionUpdatePage();
    expect(await competitionUpdatePage.getPageTitle()).to.eq('kidoleApp.competition.home.createOrEditLabel');
    await competitionUpdatePage.cancel();
  });

  it('should create and save Competitions', async () => {
    const nbButtonsBeforeCreate = await competitionComponentsPage.countDeleteButtons();

    await competitionComponentsPage.clickOnCreateButton();
    await promise.all([
      competitionUpdatePage.setCompetitionNameInput('competitionName'),
      competitionUpdatePage.setStartInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      competitionUpdatePage.setEndInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      competitionUpdatePage.setDateLimitInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      competitionUpdatePage.setDetailInput('detail'),
      competitionUpdatePage.setRuleInput(absolutePath),
      competitionUpdatePage.userSelectLastOption()
    ]);
    expect(await competitionUpdatePage.getCompetitionNameInput()).to.eq(
      'competitionName',
      'Expected CompetitionName value to be equals to competitionName'
    );
    expect(await competitionUpdatePage.getStartInput()).to.contain('2001-01-01T02:30', 'Expected start value to be equals to 2000-12-31');
    expect(await competitionUpdatePage.getEndInput()).to.contain('2001-01-01T02:30', 'Expected end value to be equals to 2000-12-31');
    expect(await competitionUpdatePage.getDateLimitInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateLimit value to be equals to 2000-12-31'
    );
    expect(await competitionUpdatePage.getDetailInput()).to.eq('detail', 'Expected Detail value to be equals to detail');
    expect(await competitionUpdatePage.getRuleInput()).to.endsWith(
      fileNameToUpload,
      'Expected Rule value to be end with ' + fileNameToUpload
    );
    await competitionUpdatePage.save();
    expect(await competitionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await competitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Competition', async () => {
    const nbButtonsBeforeDelete = await competitionComponentsPage.countDeleteButtons();
    await competitionComponentsPage.clickOnLastDeleteButton();

    competitionDeleteDialog = new CompetitionDeleteDialog();
    expect(await competitionDeleteDialog.getDialogTitle()).to.eq('kidoleApp.competition.delete.question');
    await competitionDeleteDialog.clickOnConfirmButton();

    expect(await competitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
