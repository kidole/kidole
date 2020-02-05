import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LocalisationComponentsPage, LocalisationDeleteDialog, LocalisationUpdatePage } from './localisation.page-object';

const expect = chai.expect;

describe('Localisation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let localisationComponentsPage: LocalisationComponentsPage;
  let localisationUpdatePage: LocalisationUpdatePage;
  let localisationDeleteDialog: LocalisationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Localisations', async () => {
    await navBarPage.goToEntity('localisation');
    localisationComponentsPage = new LocalisationComponentsPage();
    await browser.wait(ec.visibilityOf(localisationComponentsPage.title), 5000);
    expect(await localisationComponentsPage.getTitle()).to.eq('kidoleApp.localisation.home.title');
  });

  it('should load create Localisation page', async () => {
    await localisationComponentsPage.clickOnCreateButton();
    localisationUpdatePage = new LocalisationUpdatePage();
    expect(await localisationUpdatePage.getPageTitle()).to.eq('kidoleApp.localisation.home.createOrEditLabel');
    await localisationUpdatePage.cancel();
  });

  it('should create and save Localisations', async () => {
    const nbButtonsBeforeCreate = await localisationComponentsPage.countDeleteButtons();

    await localisationComponentsPage.clickOnCreateButton();
    await promise.all([
      localisationUpdatePage.setLocalisationNameInput('localisationName'),
      localisationUpdatePage.setLocalisationLatitudeInput('5'),
      localisationUpdatePage.setLocalisationLongitudeInput('5'),
      localisationUpdatePage.setLocalisationCountryInput('localisationCountry'),
      localisationUpdatePage.setLocalisationTownInput('localisationTown'),
      localisationUpdatePage.setLocalisationRegionInput('localisationRegion'),
      localisationUpdatePage.setLocalisationLocalityInput('localisationLocality'),
      localisationUpdatePage.competitionSelectLastOption(),
      localisationUpdatePage.prestationServiceSelectLastOption()
    ]);
    expect(await localisationUpdatePage.getLocalisationNameInput()).to.eq(
      'localisationName',
      'Expected LocalisationName value to be equals to localisationName'
    );
    expect(await localisationUpdatePage.getLocalisationLatitudeInput()).to.eq('5', 'Expected localisationLatitude value to be equals to 5');
    expect(await localisationUpdatePage.getLocalisationLongitudeInput()).to.eq(
      '5',
      'Expected localisationLongitude value to be equals to 5'
    );
    expect(await localisationUpdatePage.getLocalisationCountryInput()).to.eq(
      'localisationCountry',
      'Expected LocalisationCountry value to be equals to localisationCountry'
    );
    expect(await localisationUpdatePage.getLocalisationTownInput()).to.eq(
      'localisationTown',
      'Expected LocalisationTown value to be equals to localisationTown'
    );
    expect(await localisationUpdatePage.getLocalisationRegionInput()).to.eq(
      'localisationRegion',
      'Expected LocalisationRegion value to be equals to localisationRegion'
    );
    expect(await localisationUpdatePage.getLocalisationLocalityInput()).to.eq(
      'localisationLocality',
      'Expected LocalisationLocality value to be equals to localisationLocality'
    );
    const selectedIsSite = localisationUpdatePage.getIsSiteInput();
    if (await selectedIsSite.isSelected()) {
      await localisationUpdatePage.getIsSiteInput().click();
      expect(await localisationUpdatePage.getIsSiteInput().isSelected(), 'Expected isSite not to be selected').to.be.false;
    } else {
      await localisationUpdatePage.getIsSiteInput().click();
      expect(await localisationUpdatePage.getIsSiteInput().isSelected(), 'Expected isSite to be selected').to.be.true;
    }
    await localisationUpdatePage.save();
    expect(await localisationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await localisationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Localisation', async () => {
    const nbButtonsBeforeDelete = await localisationComponentsPage.countDeleteButtons();
    await localisationComponentsPage.clickOnLastDeleteButton();

    localisationDeleteDialog = new LocalisationDeleteDialog();
    expect(await localisationDeleteDialog.getDialogTitle()).to.eq('kidoleApp.localisation.delete.question');
    await localisationDeleteDialog.clickOnConfirmButton();

    expect(await localisationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
