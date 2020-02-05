import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DisciplineComponentsPage, DisciplineDeleteDialog, DisciplineUpdatePage } from './discipline.page-object';

const expect = chai.expect;

describe('Discipline e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let disciplineComponentsPage: DisciplineComponentsPage;
  let disciplineUpdatePage: DisciplineUpdatePage;
  let disciplineDeleteDialog: DisciplineDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Disciplines', async () => {
    await navBarPage.goToEntity('discipline');
    disciplineComponentsPage = new DisciplineComponentsPage();
    await browser.wait(ec.visibilityOf(disciplineComponentsPage.title), 5000);
    expect(await disciplineComponentsPage.getTitle()).to.eq('kidoleApp.discipline.home.title');
  });

  it('should load create Discipline page', async () => {
    await disciplineComponentsPage.clickOnCreateButton();
    disciplineUpdatePage = new DisciplineUpdatePage();
    expect(await disciplineUpdatePage.getPageTitle()).to.eq('kidoleApp.discipline.home.createOrEditLabel');
    await disciplineUpdatePage.cancel();
  });

  it('should create and save Disciplines', async () => {
    const nbButtonsBeforeCreate = await disciplineComponentsPage.countDeleteButtons();

    await disciplineComponentsPage.clickOnCreateButton();
    await promise.all([
      disciplineUpdatePage.setDisciplineNameInput('disciplineName'),
      disciplineUpdatePage.sexGenderSelectLastOption(),
      disciplineUpdatePage.competitionSelectLastOption(),
      disciplineUpdatePage.phaseSelectLastOption()
    ]);
    expect(await disciplineUpdatePage.getDisciplineNameInput()).to.eq(
      'disciplineName',
      'Expected DisciplineName value to be equals to disciplineName'
    );
    await disciplineUpdatePage.save();
    expect(await disciplineUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await disciplineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Discipline', async () => {
    const nbButtonsBeforeDelete = await disciplineComponentsPage.countDeleteButtons();
    await disciplineComponentsPage.clickOnLastDeleteButton();

    disciplineDeleteDialog = new DisciplineDeleteDialog();
    expect(await disciplineDeleteDialog.getDialogTitle()).to.eq('kidoleApp.discipline.delete.question');
    await disciplineDeleteDialog.clickOnConfirmButton();

    expect(await disciplineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
