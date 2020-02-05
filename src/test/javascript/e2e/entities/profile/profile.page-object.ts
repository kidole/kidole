import { element, by, ElementFinder } from 'protractor';

export class ProfileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-profile div table .btn-danger'));
  title = element.all(by.css('jhi-profile div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProfileUpdatePage {
  pageTitle = element(by.id('jhi-profile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  genderSelect = element(by.id('field_gender'));
  photoInput = element(by.id('file_photo'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  placeOfBbirthInput = element(by.id('field_placeOfBbirth'));
  clubOriginInput = element(by.id('field_clubOrigin'));
  nationalityInput = element(by.id('field_nationality'));
  heightInput = element(by.id('field_height'));
  weightInput = element(by.id('field_weight'));
  manualityInput = element(by.id('field_manuality'));
  nicInput = element(by.id('field_nic'));
  phoneInput = element(by.id('field_phone'));
  disciplineInput = element(by.id('field_discipline'));
  categorieInput = element(by.id('field_categorie'));
  teamNameInput = element(by.id('field_teamName'));
  functionOnInput = element(by.id('field_functionOn'));
  titleAsInput = element(by.id('field_titleAs'));
  residentCityInput = element(by.id('field_residentCity'));
  pressIDInput = element(by.id('field_pressID'));
  pressAgenceInput = element(by.id('field_pressAgence'));
  bataillonRattachementInput = element(by.id('field_bataillonRattachement'));
  socialDenominationInput = element(by.id('field_socialDenomination'));
  locationBuildingInput = element(by.id('field_locationBuilding'));
  userSelect = element(by.id('field_user'));
  accreditationSelect = element(by.id('field_accreditation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPhotoInput(photo: string): Promise<void> {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput(): Promise<string> {
    return await this.photoInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setPlaceOfBbirthInput(placeOfBbirth: string): Promise<void> {
    await this.placeOfBbirthInput.sendKeys(placeOfBbirth);
  }

  async getPlaceOfBbirthInput(): Promise<string> {
    return await this.placeOfBbirthInput.getAttribute('value');
  }

  async setClubOriginInput(clubOrigin: string): Promise<void> {
    await this.clubOriginInput.sendKeys(clubOrigin);
  }

  async getClubOriginInput(): Promise<string> {
    return await this.clubOriginInput.getAttribute('value');
  }

  async setNationalityInput(nationality: string): Promise<void> {
    await this.nationalityInput.sendKeys(nationality);
  }

  async getNationalityInput(): Promise<string> {
    return await this.nationalityInput.getAttribute('value');
  }

  async setHeightInput(height: string): Promise<void> {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput(): Promise<string> {
    return await this.heightInput.getAttribute('value');
  }

  async setWeightInput(weight: string): Promise<void> {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput(): Promise<string> {
    return await this.weightInput.getAttribute('value');
  }

  async setManualityInput(manuality: string): Promise<void> {
    await this.manualityInput.sendKeys(manuality);
  }

  async getManualityInput(): Promise<string> {
    return await this.manualityInput.getAttribute('value');
  }

  async setNicInput(nic: string): Promise<void> {
    await this.nicInput.sendKeys(nic);
  }

  async getNicInput(): Promise<string> {
    return await this.nicInput.getAttribute('value');
  }

  async setPhoneInput(phone: string): Promise<void> {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput(): Promise<string> {
    return await this.phoneInput.getAttribute('value');
  }

  async setDisciplineInput(discipline: string): Promise<void> {
    await this.disciplineInput.sendKeys(discipline);
  }

  async getDisciplineInput(): Promise<string> {
    return await this.disciplineInput.getAttribute('value');
  }

  async setCategorieInput(categorie: string): Promise<void> {
    await this.categorieInput.sendKeys(categorie);
  }

  async getCategorieInput(): Promise<string> {
    return await this.categorieInput.getAttribute('value');
  }

  async setTeamNameInput(teamName: string): Promise<void> {
    await this.teamNameInput.sendKeys(teamName);
  }

  async getTeamNameInput(): Promise<string> {
    return await this.teamNameInput.getAttribute('value');
  }

  async setFunctionOnInput(functionOn: string): Promise<void> {
    await this.functionOnInput.sendKeys(functionOn);
  }

  async getFunctionOnInput(): Promise<string> {
    return await this.functionOnInput.getAttribute('value');
  }

  async setTitleAsInput(titleAs: string): Promise<void> {
    await this.titleAsInput.sendKeys(titleAs);
  }

  async getTitleAsInput(): Promise<string> {
    return await this.titleAsInput.getAttribute('value');
  }

  async setResidentCityInput(residentCity: string): Promise<void> {
    await this.residentCityInput.sendKeys(residentCity);
  }

  async getResidentCityInput(): Promise<string> {
    return await this.residentCityInput.getAttribute('value');
  }

  async setPressIDInput(pressID: string): Promise<void> {
    await this.pressIDInput.sendKeys(pressID);
  }

  async getPressIDInput(): Promise<string> {
    return await this.pressIDInput.getAttribute('value');
  }

  async setPressAgenceInput(pressAgence: string): Promise<void> {
    await this.pressAgenceInput.sendKeys(pressAgence);
  }

  async getPressAgenceInput(): Promise<string> {
    return await this.pressAgenceInput.getAttribute('value');
  }

  async setBataillonRattachementInput(bataillonRattachement: string): Promise<void> {
    await this.bataillonRattachementInput.sendKeys(bataillonRattachement);
  }

  async getBataillonRattachementInput(): Promise<string> {
    return await this.bataillonRattachementInput.getAttribute('value');
  }

  async setSocialDenominationInput(socialDenomination: string): Promise<void> {
    await this.socialDenominationInput.sendKeys(socialDenomination);
  }

  async getSocialDenominationInput(): Promise<string> {
    return await this.socialDenominationInput.getAttribute('value');
  }

  async setLocationBuildingInput(locationBuilding: string): Promise<void> {
    await this.locationBuildingInput.sendKeys(locationBuilding);
  }

  async getLocationBuildingInput(): Promise<string> {
    return await this.locationBuildingInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async accreditationSelectLastOption(): Promise<void> {
    await this.accreditationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async accreditationSelectOption(option: string): Promise<void> {
    await this.accreditationSelect.sendKeys(option);
  }

  getAccreditationSelect(): ElementFinder {
    return this.accreditationSelect;
  }

  async getAccreditationSelectedOption(): Promise<string> {
    return await this.accreditationSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProfileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-profile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-profile'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
