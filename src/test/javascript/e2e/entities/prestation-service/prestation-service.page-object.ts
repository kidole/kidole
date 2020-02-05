import { element, by, ElementFinder } from 'protractor';

export class PrestationServiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-prestation-service div table .btn-danger'));
  title = element.all(by.css('jhi-prestation-service div h2#page-heading span')).first();

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

export class PrestationServiceUpdatePage {
  pageTitle = element(by.id('jhi-prestation-service-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  prestationServiceNameInput = element(by.id('field_prestationServiceName'));
  prestationServiceNameStateSelect = element(by.id('field_prestationServiceNameState'));
  prestationServiceNameDetailInput = element(by.id('field_prestationServiceNameDetail'));
  prestationServiceNameImageInput = element(by.id('file_prestationServiceNameImage'));
  rubriqueSelect = element(by.id('field_rubrique'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPrestationServiceNameInput(prestationServiceName: string): Promise<void> {
    await this.prestationServiceNameInput.sendKeys(prestationServiceName);
  }

  async getPrestationServiceNameInput(): Promise<string> {
    return await this.prestationServiceNameInput.getAttribute('value');
  }

  async setPrestationServiceNameStateSelect(prestationServiceNameState: string): Promise<void> {
    await this.prestationServiceNameStateSelect.sendKeys(prestationServiceNameState);
  }

  async getPrestationServiceNameStateSelect(): Promise<string> {
    return await this.prestationServiceNameStateSelect.element(by.css('option:checked')).getText();
  }

  async prestationServiceNameStateSelectLastOption(): Promise<void> {
    await this.prestationServiceNameStateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPrestationServiceNameDetailInput(prestationServiceNameDetail: string): Promise<void> {
    await this.prestationServiceNameDetailInput.sendKeys(prestationServiceNameDetail);
  }

  async getPrestationServiceNameDetailInput(): Promise<string> {
    return await this.prestationServiceNameDetailInput.getAttribute('value');
  }

  async setPrestationServiceNameImageInput(prestationServiceNameImage: string): Promise<void> {
    await this.prestationServiceNameImageInput.sendKeys(prestationServiceNameImage);
  }

  async getPrestationServiceNameImageInput(): Promise<string> {
    return await this.prestationServiceNameImageInput.getAttribute('value');
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

export class PrestationServiceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-prestationService-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-prestationService'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
