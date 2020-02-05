import { element, by, ElementFinder } from 'protractor';

export class RubriqueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-rubrique div table .btn-danger'));
  title = element.all(by.css('jhi-rubrique div h2#page-heading span')).first();

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

export class RubriqueUpdatePage {
  pageTitle = element(by.id('jhi-rubrique-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  rubriqueNameInput = element(by.id('field_rubriqueName'));
  rubriqueDetailsInput = element(by.id('field_rubriqueDetails'));
  rubriqueImageInput = element(by.id('file_rubriqueImage'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRubriqueNameInput(rubriqueName: string): Promise<void> {
    await this.rubriqueNameInput.sendKeys(rubriqueName);
  }

  async getRubriqueNameInput(): Promise<string> {
    return await this.rubriqueNameInput.getAttribute('value');
  }

  async setRubriqueDetailsInput(rubriqueDetails: string): Promise<void> {
    await this.rubriqueDetailsInput.sendKeys(rubriqueDetails);
  }

  async getRubriqueDetailsInput(): Promise<string> {
    return await this.rubriqueDetailsInput.getAttribute('value');
  }

  async setRubriqueImageInput(rubriqueImage: string): Promise<void> {
    await this.rubriqueImageInput.sendKeys(rubriqueImage);
  }

  async getRubriqueImageInput(): Promise<string> {
    return await this.rubriqueImageInput.getAttribute('value');
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

export class RubriqueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-rubrique-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-rubrique'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
