import { element, by, ElementFinder } from 'protractor';

export class DelegationMembersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-delegation-members div table .btn-danger'));
  title = element.all(by.css('jhi-delegation-members div h2#page-heading span')).first();

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

export class DelegationMembersUpdatePage {
  pageTitle = element(by.id('jhi-delegation-members-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  delegationMembersStateInput = element(by.id('field_delegationMembersState'));
  delegationMembersCodeInput = element(by.id('field_delegationMembersCode'));
  delegationMembersDetailInput = element(by.id('field_delegationMembersDetail'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDelegationMembersStateInput(delegationMembersState: string): Promise<void> {
    await this.delegationMembersStateInput.sendKeys(delegationMembersState);
  }

  async getDelegationMembersStateInput(): Promise<string> {
    return await this.delegationMembersStateInput.getAttribute('value');
  }

  async setDelegationMembersCodeInput(delegationMembersCode: string): Promise<void> {
    await this.delegationMembersCodeInput.sendKeys(delegationMembersCode);
  }

  async getDelegationMembersCodeInput(): Promise<string> {
    return await this.delegationMembersCodeInput.getAttribute('value');
  }

  async setDelegationMembersDetailInput(delegationMembersDetail: string): Promise<void> {
    await this.delegationMembersDetailInput.sendKeys(delegationMembersDetail);
  }

  async getDelegationMembersDetailInput(): Promise<string> {
    return await this.delegationMembersDetailInput.getAttribute('value');
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

export class DelegationMembersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-delegationMembers-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-delegationMembers'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
