import { element, by, ElementFinder } from 'protractor';

export class LocalisationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-localisation div table .btn-danger'));
  title = element.all(by.css('jhi-localisation div h2#page-heading span')).first();

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

export class LocalisationUpdatePage {
  pageTitle = element(by.id('jhi-localisation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  localisationNameInput = element(by.id('field_localisationName'));
  localisationLatitudeInput = element(by.id('field_localisationLatitude'));
  localisationLongitudeInput = element(by.id('field_localisationLongitude'));
  localisationCountryInput = element(by.id('field_localisationCountry'));
  localisationTownInput = element(by.id('field_localisationTown'));
  localisationRegionInput = element(by.id('field_localisationRegion'));
  localisationLocalityInput = element(by.id('field_localisationLocality'));
  isSiteInput = element(by.id('field_isSite'));
  competitionSelect = element(by.id('field_competition'));
  prestationServiceSelect = element(by.id('field_prestationService'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLocalisationNameInput(localisationName: string): Promise<void> {
    await this.localisationNameInput.sendKeys(localisationName);
  }

  async getLocalisationNameInput(): Promise<string> {
    return await this.localisationNameInput.getAttribute('value');
  }

  async setLocalisationLatitudeInput(localisationLatitude: string): Promise<void> {
    await this.localisationLatitudeInput.sendKeys(localisationLatitude);
  }

  async getLocalisationLatitudeInput(): Promise<string> {
    return await this.localisationLatitudeInput.getAttribute('value');
  }

  async setLocalisationLongitudeInput(localisationLongitude: string): Promise<void> {
    await this.localisationLongitudeInput.sendKeys(localisationLongitude);
  }

  async getLocalisationLongitudeInput(): Promise<string> {
    return await this.localisationLongitudeInput.getAttribute('value');
  }

  async setLocalisationCountryInput(localisationCountry: string): Promise<void> {
    await this.localisationCountryInput.sendKeys(localisationCountry);
  }

  async getLocalisationCountryInput(): Promise<string> {
    return await this.localisationCountryInput.getAttribute('value');
  }

  async setLocalisationTownInput(localisationTown: string): Promise<void> {
    await this.localisationTownInput.sendKeys(localisationTown);
  }

  async getLocalisationTownInput(): Promise<string> {
    return await this.localisationTownInput.getAttribute('value');
  }

  async setLocalisationRegionInput(localisationRegion: string): Promise<void> {
    await this.localisationRegionInput.sendKeys(localisationRegion);
  }

  async getLocalisationRegionInput(): Promise<string> {
    return await this.localisationRegionInput.getAttribute('value');
  }

  async setLocalisationLocalityInput(localisationLocality: string): Promise<void> {
    await this.localisationLocalityInput.sendKeys(localisationLocality);
  }

  async getLocalisationLocalityInput(): Promise<string> {
    return await this.localisationLocalityInput.getAttribute('value');
  }

  getIsSiteInput(): ElementFinder {
    return this.isSiteInput;
  }

  async competitionSelectLastOption(): Promise<void> {
    await this.competitionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async competitionSelectOption(option: string): Promise<void> {
    await this.competitionSelect.sendKeys(option);
  }

  getCompetitionSelect(): ElementFinder {
    return this.competitionSelect;
  }

  async getCompetitionSelectedOption(): Promise<string> {
    return await this.competitionSelect.element(by.css('option:checked')).getText();
  }

  async prestationServiceSelectLastOption(): Promise<void> {
    await this.prestationServiceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async prestationServiceSelectOption(option: string): Promise<void> {
    await this.prestationServiceSelect.sendKeys(option);
  }

  getPrestationServiceSelect(): ElementFinder {
    return this.prestationServiceSelect;
  }

  async getPrestationServiceSelectedOption(): Promise<string> {
    return await this.prestationServiceSelect.element(by.css('option:checked')).getText();
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

export class LocalisationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-localisation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-localisation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
