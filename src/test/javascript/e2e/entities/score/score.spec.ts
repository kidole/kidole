import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScoreComponentsPage, ScoreDeleteDialog, ScoreUpdatePage } from './score.page-object';

const expect = chai.expect;

describe('Score e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scoreComponentsPage: ScoreComponentsPage;
  let scoreUpdatePage: ScoreUpdatePage;
  let scoreDeleteDialog: ScoreDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scores', async () => {
    await navBarPage.goToEntity('score');
    scoreComponentsPage = new ScoreComponentsPage();
    await browser.wait(ec.visibilityOf(scoreComponentsPage.title), 5000);
    expect(await scoreComponentsPage.getTitle()).to.eq('kidoleApp.score.home.title');
  });

  it('should load create Score page', async () => {
    await scoreComponentsPage.clickOnCreateButton();
    scoreUpdatePage = new ScoreUpdatePage();
    expect(await scoreUpdatePage.getPageTitle()).to.eq('kidoleApp.score.home.createOrEditLabel');
    await scoreUpdatePage.cancel();
  });

  it('should create and save Scores', async () => {
    const nbButtonsBeforeCreate = await scoreComponentsPage.countDeleteButtons();

    await scoreComponentsPage.clickOnCreateButton();
    await promise.all([
      scoreUpdatePage.setScoreNameInput('scoreName'),
      scoreUpdatePage.setScoreIndexInput('5'),
      scoreUpdatePage.setScoreValueInput('5'),
      scoreUpdatePage.setScoreUnitInput('scoreUnit'),
      scoreUpdatePage.teamSelectLastOption(),
      scoreUpdatePage.confrontationSelectLastOption()
    ]);
    expect(await scoreUpdatePage.getScoreNameInput()).to.eq('scoreName', 'Expected ScoreName value to be equals to scoreName');
    expect(await scoreUpdatePage.getScoreIndexInput()).to.eq('5', 'Expected scoreIndex value to be equals to 5');
    expect(await scoreUpdatePage.getScoreValueInput()).to.eq('5', 'Expected scoreValue value to be equals to 5');
    expect(await scoreUpdatePage.getScoreUnitInput()).to.eq('scoreUnit', 'Expected ScoreUnit value to be equals to scoreUnit');
    await scoreUpdatePage.save();
    expect(await scoreUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await scoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Score', async () => {
    const nbButtonsBeforeDelete = await scoreComponentsPage.countDeleteButtons();
    await scoreComponentsPage.clickOnLastDeleteButton();

    scoreDeleteDialog = new ScoreDeleteDialog();
    expect(await scoreDeleteDialog.getDialogTitle()).to.eq('kidoleApp.score.delete.question');
    await scoreDeleteDialog.clickOnConfirmButton();

    expect(await scoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
