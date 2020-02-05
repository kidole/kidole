import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CompetitionServicesOfferComponentsPage,
  CompetitionServicesOfferDeleteDialog,
  CompetitionServicesOfferUpdatePage
} from './competition-services-offer.page-object';

const expect = chai.expect;

describe('CompetitionServicesOffer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let competitionServicesOfferComponentsPage: CompetitionServicesOfferComponentsPage;
  let competitionServicesOfferUpdatePage: CompetitionServicesOfferUpdatePage;
  let competitionServicesOfferDeleteDialog: CompetitionServicesOfferDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CompetitionServicesOffers', async () => {
    await navBarPage.goToEntity('competition-services-offer');
    competitionServicesOfferComponentsPage = new CompetitionServicesOfferComponentsPage();
    await browser.wait(ec.visibilityOf(competitionServicesOfferComponentsPage.title), 5000);
    expect(await competitionServicesOfferComponentsPage.getTitle()).to.eq('kidoleApp.competitionServicesOffer.home.title');
  });

  it('should load create CompetitionServicesOffer page', async () => {
    await competitionServicesOfferComponentsPage.clickOnCreateButton();
    competitionServicesOfferUpdatePage = new CompetitionServicesOfferUpdatePage();
    expect(await competitionServicesOfferUpdatePage.getPageTitle()).to.eq('kidoleApp.competitionServicesOffer.home.createOrEditLabel');
    await competitionServicesOfferUpdatePage.cancel();
  });

  it('should create and save CompetitionServicesOffers', async () => {
    const nbButtonsBeforeCreate = await competitionServicesOfferComponentsPage.countDeleteButtons();

    await competitionServicesOfferComponentsPage.clickOnCreateButton();
    await promise.all([
      competitionServicesOfferUpdatePage.setCompetitionServicesOfferNameInput('competitionServicesOfferName'),
      competitionServicesOfferUpdatePage.setCompetitionServicesOfferDetailInput('competitionServicesOfferDetail'),
      competitionServicesOfferUpdatePage.setCompetitionServicesOfferUrlInput('competitionServicesOfferUrl'),
      competitionServicesOfferUpdatePage.rubricSelectLastOption(),
      competitionServicesOfferUpdatePage.competitionSelectLastOption()
    ]);
    expect(await competitionServicesOfferUpdatePage.getCompetitionServicesOfferNameInput()).to.eq(
      'competitionServicesOfferName',
      'Expected CompetitionServicesOfferName value to be equals to competitionServicesOfferName'
    );
    expect(await competitionServicesOfferUpdatePage.getCompetitionServicesOfferDetailInput()).to.eq(
      'competitionServicesOfferDetail',
      'Expected CompetitionServicesOfferDetail value to be equals to competitionServicesOfferDetail'
    );
    expect(await competitionServicesOfferUpdatePage.getCompetitionServicesOfferUrlInput()).to.eq(
      'competitionServicesOfferUrl',
      'Expected CompetitionServicesOfferUrl value to be equals to competitionServicesOfferUrl'
    );
    await competitionServicesOfferUpdatePage.save();
    expect(await competitionServicesOfferUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await competitionServicesOfferComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CompetitionServicesOffer', async () => {
    const nbButtonsBeforeDelete = await competitionServicesOfferComponentsPage.countDeleteButtons();
    await competitionServicesOfferComponentsPage.clickOnLastDeleteButton();

    competitionServicesOfferDeleteDialog = new CompetitionServicesOfferDeleteDialog();
    expect(await competitionServicesOfferDeleteDialog.getDialogTitle()).to.eq('kidoleApp.competitionServicesOffer.delete.question');
    await competitionServicesOfferDeleteDialog.clickOnConfirmButton();

    expect(await competitionServicesOfferComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
