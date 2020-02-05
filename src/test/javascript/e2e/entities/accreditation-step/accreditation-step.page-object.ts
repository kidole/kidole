import { element, by, ElementFinder } from 'protractor';

export class AccreditationStepComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-accreditation-step div table .btn-danger'));
  title = element.all(by.css('jhi-accreditation-step div h2#page-heading span')).first();

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

export class AccreditationStepUpdatePage {
  pageTitle = element(by.id('jhi-accreditation-step-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  startTimeInput = element(by.id('field_startTime'));
  endTimeInput = element(by.id('field_endTime'));
  accreditationStepnumberInput = element(by.id('field_accreditationStepnumber'));
  accreditationTypeSelect = element(by.id('field_accreditationType'));
  isPublicInput = element(by.id('field_isPublic'));
  uriInput = element(by.id('field_uri'));
  fieldsInput = element(by.id('field_fields'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setStartTimeInput(startTime: string): Promise<void> {
    await this.startTimeInput.sendKeys(startTime);
  }

  async getStartTimeInput(): Promise<string> {
    return await this.startTimeInput.getAttribute('value');
  }

  async setEndTimeInput(endTime: string): Promise<void> {
    await this.endTimeInput.sendKeys(endTime);
  }

  async getEndTimeInput(): Promise<string> {
    return await this.endTimeInput.getAttribute('value');
  }

  async setAccreditationStepnumberInput(accreditationStepnumber: string): Promise<void> {
    await this.accreditationStepnumberInput.sendKeys(accreditationStepnumber);
  }

  async getAccreditationStepnumberInput(): Promise<string> {
    return await this.accreditationStepnumberInput.getAttribute('value');
  }

  async setAccreditationTypeSelect(accreditationType: string): Promise<void> {
    await this.accreditationTypeSelect.sendKeys(accreditationType);
  }

  async getAccreditationTypeSelect(): Promise<string> {
    return await this.accreditationTypeSelect.element(by.css('option:checked')).getText();
  }

  async accreditationTypeSelectLastOption(): Promise<void> {
    await this.accreditationTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getIsPublicInput(): ElementFinder {
    return this.isPublicInput;
  }
  getUriInput(): ElementFinder {
    return this.uriInput;
  }
  async setFieldsInput(fields: string): Promise<void> {
    await this.fieldsInput.sendKeys(fields);
  }

  async getFieldsInput(): Promise<string> {
    return await this.fieldsInput.getAttribute('value');
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

export class AccreditationStepDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-accreditationStep-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-accreditationStep'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
