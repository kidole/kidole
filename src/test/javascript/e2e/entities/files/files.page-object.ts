import { element, by, ElementFinder } from 'protractor';

export class FilesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-files div table .btn-danger'));
  title = element.all(by.css('jhi-files div h2#page-heading span')).first();

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

export class FilesUpdatePage {
  pageTitle = element(by.id('jhi-files-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fileNameInput = element(by.id('field_fileName'));
  fileTypeInput = element(by.id('field_fileType'));
  filePublicInput = element(by.id('field_filePublic'));
  fileToUploadInput = element(by.id('file_fileToUpload'));
  userSelect = element(by.id('field_user'));
  competitionServicesOfferSelect = element(by.id('field_competitionServicesOffer'));
  prestationServiceSelect = element(by.id('field_prestationService'));
  rubriqueSelect = element(by.id('field_rubrique'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFileNameInput(fileName: string): Promise<void> {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput(): Promise<string> {
    return await this.fileNameInput.getAttribute('value');
  }

  async setFileTypeInput(fileType: string): Promise<void> {
    await this.fileTypeInput.sendKeys(fileType);
  }

  async getFileTypeInput(): Promise<string> {
    return await this.fileTypeInput.getAttribute('value');
  }

  getFilePublicInput(): ElementFinder {
    return this.filePublicInput;
  }
  async setFileToUploadInput(fileToUpload: string): Promise<void> {
    await this.fileToUploadInput.sendKeys(fileToUpload);
  }

  async getFileToUploadInput(): Promise<string> {
    return await this.fileToUploadInput.getAttribute('value');
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

  async competitionServicesOfferSelectLastOption(): Promise<void> {
    await this.competitionServicesOfferSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async competitionServicesOfferSelectOption(option: string): Promise<void> {
    await this.competitionServicesOfferSelect.sendKeys(option);
  }

  getCompetitionServicesOfferSelect(): ElementFinder {
    return this.competitionServicesOfferSelect;
  }

  async getCompetitionServicesOfferSelectedOption(): Promise<string> {
    return await this.competitionServicesOfferSelect.element(by.css('option:checked')).getText();
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

  async rubriqueSelectLastOption(): Promise<void> {
    await this.rubriqueSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async rubriqueSelectOption(option: string): Promise<void> {
    await this.rubriqueSelect.sendKeys(option);
  }

  getRubriqueSelect(): ElementFinder {
    return this.rubriqueSelect;
  }

  async getRubriqueSelectedOption(): Promise<string> {
    return await this.rubriqueSelect.element(by.css('option:checked')).getText();
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

export class FilesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-files-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-files'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
