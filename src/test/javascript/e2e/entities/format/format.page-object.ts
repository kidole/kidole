import { element, by, ElementFinder } from 'protractor';

export class FormatComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-format div table .btn-danger'));
  title = element.all(by.css('jhi-format div h2#page-heading span')).first();

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

export class FormatUpdatePage {
  pageTitle = element(by.id('jhi-format-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  formatNameInput = element(by.id('field_formatName'));
  winerQtyInput = element(by.id('field_winerQty'));
  phaseSelect = element(by.id('field_phase'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFormatNameInput(formatName: string): Promise<void> {
    await this.formatNameInput.sendKeys(formatName);
  }

  async getFormatNameInput(): Promise<string> {
    return await this.formatNameInput.getAttribute('value');
  }

  async setWinerQtyInput(winerQty: string): Promise<void> {
    await this.winerQtyInput.sendKeys(winerQty);
  }

  async getWinerQtyInput(): Promise<string> {
    return await this.winerQtyInput.getAttribute('value');
  }

  async phaseSelectLastOption(): Promise<void> {
    await this.phaseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async phaseSelectOption(option: string): Promise<void> {
    await this.phaseSelect.sendKeys(option);
  }

  getPhaseSelect(): ElementFinder {
    return this.phaseSelect;
  }

  async getPhaseSelectedOption(): Promise<string> {
    return await this.phaseSelect.element(by.css('option:checked')).getText();
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

export class FormatDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-format-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-format'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
