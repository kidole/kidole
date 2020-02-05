import { element, by, ElementFinder } from 'protractor';

export class JourneeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-journee div table .btn-danger'));
  title = element.all(by.css('jhi-journee div h2#page-heading span')).first();

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

export class JourneeUpdatePage {
  pageTitle = element(by.id('jhi-journee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  journeeNameInput = element(by.id('field_journeeName'));
  phaseSelect = element(by.id('field_phase'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setJourneeNameInput(journeeName: string): Promise<void> {
    await this.journeeNameInput.sendKeys(journeeName);
  }

  async getJourneeNameInput(): Promise<string> {
    return await this.journeeNameInput.getAttribute('value');
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

export class JourneeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-journee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-journee'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
