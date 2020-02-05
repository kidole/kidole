import { element, by, ElementFinder } from 'protractor';

export class PoulesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-poules div table .btn-danger'));
  title = element.all(by.css('jhi-poules div h2#page-heading span')).first();

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

export class PoulesUpdatePage {
  pageTitle = element(by.id('jhi-poules-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  poulesNameInput = element(by.id('field_poulesName'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPoulesNameInput(poulesName: string): Promise<void> {
    await this.poulesNameInput.sendKeys(poulesName);
  }

  async getPoulesNameInput(): Promise<string> {
    return await this.poulesNameInput.getAttribute('value');
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

export class PoulesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-poules-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-poules'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
