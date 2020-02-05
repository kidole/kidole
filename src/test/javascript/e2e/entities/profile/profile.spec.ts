import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileComponentsPage, ProfileDeleteDialog, ProfileUpdatePage } from './profile.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Profile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileComponentsPage: ProfileComponentsPage;
  let profileUpdatePage: ProfileUpdatePage;
  let profileDeleteDialog: ProfileDeleteDialog;
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

  it('should load Profiles', async () => {
    await navBarPage.goToEntity('profile');
    profileComponentsPage = new ProfileComponentsPage();
    await browser.wait(ec.visibilityOf(profileComponentsPage.title), 5000);
    expect(await profileComponentsPage.getTitle()).to.eq('kidoleApp.profile.home.title');
  });

  it('should load create Profile page', async () => {
    await profileComponentsPage.clickOnCreateButton();
    profileUpdatePage = new ProfileUpdatePage();
    expect(await profileUpdatePage.getPageTitle()).to.eq('kidoleApp.profile.home.createOrEditLabel');
    await profileUpdatePage.cancel();
  });

  it('should create and save Profiles', async () => {
    const nbButtonsBeforeCreate = await profileComponentsPage.countDeleteButtons();

    await profileComponentsPage.clickOnCreateButton();
    await promise.all([
      profileUpdatePage.genderSelectLastOption(),
      profileUpdatePage.setPhotoInput(absolutePath),
      profileUpdatePage.setDateOfBirthInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      profileUpdatePage.setPlaceOfBbirthInput('placeOfBbirth'),
      profileUpdatePage.setClubOriginInput('clubOrigin'),
      profileUpdatePage.setNationalityInput('nationality'),
      profileUpdatePage.setHeightInput('5'),
      profileUpdatePage.setWeightInput('5'),
      profileUpdatePage.setManualityInput('manuality'),
      profileUpdatePage.setNicInput('nic'),
      profileUpdatePage.setPhoneInput('phone'),
      profileUpdatePage.setDisciplineInput('discipline'),
      profileUpdatePage.setCategorieInput('categorie'),
      profileUpdatePage.setTeamNameInput('teamName'),
      profileUpdatePage.setFunctionOnInput('functionOn'),
      profileUpdatePage.setTitleAsInput('titleAs'),
      profileUpdatePage.setResidentCityInput('residentCity'),
      profileUpdatePage.setPressIDInput('pressID'),
      profileUpdatePage.setPressAgenceInput('pressAgence'),
      profileUpdatePage.setBataillonRattachementInput('bataillonRattachement'),
      profileUpdatePage.setSocialDenominationInput('socialDenomination'),
      profileUpdatePage.setLocationBuildingInput('locationBuilding'),
      profileUpdatePage.userSelectLastOption(),
      profileUpdatePage.accreditationSelectLastOption()
    ]);
    expect(await profileUpdatePage.getPhotoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Photo value to be end with ' + fileNameToUpload
    );
    expect(await profileUpdatePage.getDateOfBirthInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfBirth value to be equals to 2000-12-31'
    );
    expect(await profileUpdatePage.getPlaceOfBbirthInput()).to.eq(
      'placeOfBbirth',
      'Expected PlaceOfBbirth value to be equals to placeOfBbirth'
    );
    expect(await profileUpdatePage.getClubOriginInput()).to.eq('clubOrigin', 'Expected ClubOrigin value to be equals to clubOrigin');
    expect(await profileUpdatePage.getNationalityInput()).to.eq('nationality', 'Expected Nationality value to be equals to nationality');
    expect(await profileUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await profileUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');
    expect(await profileUpdatePage.getManualityInput()).to.eq('manuality', 'Expected Manuality value to be equals to manuality');
    expect(await profileUpdatePage.getNicInput()).to.eq('nic', 'Expected Nic value to be equals to nic');
    expect(await profileUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await profileUpdatePage.getDisciplineInput()).to.eq('discipline', 'Expected Discipline value to be equals to discipline');
    expect(await profileUpdatePage.getCategorieInput()).to.eq('categorie', 'Expected Categorie value to be equals to categorie');
    expect(await profileUpdatePage.getTeamNameInput()).to.eq('teamName', 'Expected TeamName value to be equals to teamName');
    expect(await profileUpdatePage.getFunctionOnInput()).to.eq('functionOn', 'Expected FunctionOn value to be equals to functionOn');
    expect(await profileUpdatePage.getTitleAsInput()).to.eq('titleAs', 'Expected TitleAs value to be equals to titleAs');
    expect(await profileUpdatePage.getResidentCityInput()).to.eq(
      'residentCity',
      'Expected ResidentCity value to be equals to residentCity'
    );
    expect(await profileUpdatePage.getPressIDInput()).to.eq('pressID', 'Expected PressID value to be equals to pressID');
    expect(await profileUpdatePage.getPressAgenceInput()).to.eq('pressAgence', 'Expected PressAgence value to be equals to pressAgence');
    expect(await profileUpdatePage.getBataillonRattachementInput()).to.eq(
      'bataillonRattachement',
      'Expected BataillonRattachement value to be equals to bataillonRattachement'
    );
    expect(await profileUpdatePage.getSocialDenominationInput()).to.eq(
      'socialDenomination',
      'Expected SocialDenomination value to be equals to socialDenomination'
    );
    expect(await profileUpdatePage.getLocationBuildingInput()).to.eq(
      'locationBuilding',
      'Expected LocationBuilding value to be equals to locationBuilding'
    );
    await profileUpdatePage.save();
    expect(await profileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Profile', async () => {
    const nbButtonsBeforeDelete = await profileComponentsPage.countDeleteButtons();
    await profileComponentsPage.clickOnLastDeleteButton();

    profileDeleteDialog = new ProfileDeleteDialog();
    expect(await profileDeleteDialog.getDialogTitle()).to.eq('kidoleApp.profile.delete.question');
    await profileDeleteDialog.clickOnConfirmButton();

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
