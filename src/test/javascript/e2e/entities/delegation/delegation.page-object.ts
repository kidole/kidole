import { element, by, ElementFinder } from 'protractor';

export class DelegationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-delegation div table .btn-danger'));
  title = element.all(by.css('jhi-delegation div h2#page-heading span')).first();

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

export class DelegationUpdatePage {
  pageTitle = element(by.id('jhi-delegation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  delegationNameInput = element(by.id('field_delegationName'));
  delegationCountryInput = element(by.id('field_delegationCountry'));
  delegationLocalityInput = element(by.id('field_delegationLocality'));
  delegationCodeInput = element(by.id('field_delegationCode'));
  delegateMemberSelect = element(by.id('field_delegateMember'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDelegationNameInput(delegationName: string): Promise<void> {
    await this.delegationNameInput.sendKeys(delegationName);
  }

  async getDelegationNameInput(): Promise<string> {
    return await this.delegationNameInput.getAttribute('value');
  }

  async setDelegationCountryInput(delegationCountry: string): Promise<void> {
    await this.delegationCountryInput.sendKeys(delegationCountry);
  }

  async getDelegationCountryInput(): Promise<string> {
    return await this.delegationCountryInput.getAttribute('value');
  }

  async setDelegationLocalityInput(delegationLocality: string): Promise<void> {
    await this.delegationLocalityInput.sendKeys(delegationLocality);
  }

  async getDelegationLocalityInput(): Promise<string> {
    return await this.delegationLocalityInput.getAttribute('value');
  }

  async setDelegationCodeInput(delegationCode: string): Promise<void> {
    await this.delegationCodeInput.sendKeys(delegationCode);
  }

  async getDelegationCodeInput(): Promise<string> {
    return await this.delegationCodeInput.getAttribute('value');
  }

  async delegateMemberSelectLastOption(): Promise<void> {
    await this.delegateMemberSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async delegateMemberSelectOption(option: string): Promise<void> {
    await this.delegateMemberSelect.sendKeys(option);
  }

  getDelegateMemberSelect(): ElementFinder {
    return this.delegateMemberSelect;
  }

  async getDelegateMemberSelectedOption(): Promise<string> {
    return await this.delegateMemberSelect.element(by.css('option:checked')).getText();
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

export class DelegationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-delegation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-delegation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
