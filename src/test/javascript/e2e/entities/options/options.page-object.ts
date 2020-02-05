import { element, by, ElementFinder } from 'protractor';

export class OptionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-options div table .btn-danger'));
  title = element.all(by.css('jhi-options div h2#page-heading span')).first();

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

export class OptionsUpdatePage {
  pageTitle = element(by.id('jhi-options-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  optionsNameInput = element(by.id('field_optionsName'));
  optionsValue1Input = element(by.id('field_optionsValue1'));
  optionsValue2Input = element(by.id('field_optionsValue2'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOptionsNameInput(optionsName: string): Promise<void> {
    await this.optionsNameInput.sendKeys(optionsName);
  }

  async getOptionsNameInput(): Promise<string> {
    return await this.optionsNameInput.getAttribute('value');
  }

  getOptionsValue1Input(): ElementFinder {
    return this.optionsValue1Input;
  }
  async setOptionsValue2Input(optionsValue2: string): Promise<void> {
    await this.optionsValue2Input.sendKeys(optionsValue2);
  }

  async getOptionsValue2Input(): Promise<string> {
    return await this.optionsValue2Input.getAttribute('value');
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

export class OptionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-options-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-options'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
