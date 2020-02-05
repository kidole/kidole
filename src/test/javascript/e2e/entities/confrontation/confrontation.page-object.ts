import { element, by, ElementFinder } from 'protractor';

export class ConfrontationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-confrontation div table .btn-danger'));
  title = element.all(by.css('jhi-confrontation div h2#page-heading span')).first();

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

export class ConfrontationUpdatePage {
  pageTitle = element(by.id('jhi-confrontation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  confrontationNameInput = element(by.id('field_confrontationName'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  confrontationDetailsInput = element(by.id('field_confrontationDetails'));
  matchsheetSelect = element(by.id('field_matchsheet'));
  localisationSelect = element(by.id('field_localisation'));
  journeeSelect = element(by.id('field_journee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setConfrontationNameInput(confrontationName: string): Promise<void> {
    await this.confrontationNameInput.sendKeys(confrontationName);
  }

  async getConfrontationNameInput(): Promise<string> {
    return await this.confrontationNameInput.getAttribute('value');
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate: string): Promise<void> {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput(): Promise<string> {
    return await this.endDateInput.getAttribute('value');
  }

  async setConfrontationDetailsInput(confrontationDetails: string): Promise<void> {
    await this.confrontationDetailsInput.sendKeys(confrontationDetails);
  }

  async getConfrontationDetailsInput(): Promise<string> {
    return await this.confrontationDetailsInput.getAttribute('value');
  }

  async matchsheetSelectLastOption(): Promise<void> {
    await this.matchsheetSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matchsheetSelectOption(option: string): Promise<void> {
    await this.matchsheetSelect.sendKeys(option);
  }

  getMatchsheetSelect(): ElementFinder {
    return this.matchsheetSelect;
  }

  async getMatchsheetSelectedOption(): Promise<string> {
    return await this.matchsheetSelect.element(by.css('option:checked')).getText();
  }

  async localisationSelectLastOption(): Promise<void> {
    await this.localisationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async localisationSelectOption(option: string): Promise<void> {
    await this.localisationSelect.sendKeys(option);
  }

  getLocalisationSelect(): ElementFinder {
    return this.localisationSelect;
  }

  async getLocalisationSelectedOption(): Promise<string> {
    return await this.localisationSelect.element(by.css('option:checked')).getText();
  }

  async journeeSelectLastOption(): Promise<void> {
    await this.journeeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async journeeSelectOption(option: string): Promise<void> {
    await this.journeeSelect.sendKeys(option);
  }

  getJourneeSelect(): ElementFinder {
    return this.journeeSelect;
  }

  async getJourneeSelectedOption(): Promise<string> {
    return await this.journeeSelect.element(by.css('option:checked')).getText();
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

export class ConfrontationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-confrontation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-confrontation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
