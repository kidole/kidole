import { element, by, ElementFinder } from 'protractor';

export class AccreditationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-accreditation div table .btn-danger'));
  title = element.all(by.css('jhi-accreditation div h2#page-heading span')).first();

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

export class AccreditationUpdatePage {
  pageTitle = element(by.id('jhi-accreditation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  accreditationNameSelect = element(by.id('field_accreditationName'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  accreditationEmailInput = element(by.id('field_accreditationEmail'));
  accreditationStatusSelect = element(by.id('field_accreditationStatus'));
  accreditationDetailInput = element(by.id('field_accreditationDetail'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAccreditationNameSelect(accreditationName: string): Promise<void> {
    await this.accreditationNameSelect.sendKeys(accreditationName);
  }

  async getAccreditationNameSelect(): Promise<string> {
    return await this.accreditationNameSelect.element(by.css('option:checked')).getText();
  }

  async accreditationNameSelectLastOption(): Promise<void> {
    await this.accreditationNameSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setAccreditationEmailInput(accreditationEmail: string): Promise<void> {
    await this.accreditationEmailInput.sendKeys(accreditationEmail);
  }

  async getAccreditationEmailInput(): Promise<string> {
    return await this.accreditationEmailInput.getAttribute('value');
  }

  async setAccreditationStatusSelect(accreditationStatus: string): Promise<void> {
    await this.accreditationStatusSelect.sendKeys(accreditationStatus);
  }

  async getAccreditationStatusSelect(): Promise<string> {
    return await this.accreditationStatusSelect.element(by.css('option:checked')).getText();
  }

  async accreditationStatusSelectLastOption(): Promise<void> {
    await this.accreditationStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setAccreditationDetailInput(accreditationDetail: string): Promise<void> {
    await this.accreditationDetailInput.sendKeys(accreditationDetail);
  }

  async getAccreditationDetailInput(): Promise<string> {
    return await this.accreditationDetailInput.getAttribute('value');
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

export class AccreditationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-accreditation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-accreditation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
