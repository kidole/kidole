import { element, by, ElementFinder } from 'protractor';

export class TeamComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-team div table .btn-danger'));
  title = element.all(by.css('jhi-team div h2#page-heading span')).first();

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

export class TeamUpdatePage {
  pageTitle = element(by.id('jhi-team-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  teamNameInput = element(by.id('field_teamName'));
  userSelect = element(by.id('field_user'));
  confrontationSelect = element(by.id('field_confrontation'));
  poulesSelect = element(by.id('field_poules'));
  delegationSelect = element(by.id('field_delegation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTeamNameInput(teamName: string): Promise<void> {
    await this.teamNameInput.sendKeys(teamName);
  }

  async getTeamNameInput(): Promise<string> {
    return await this.teamNameInput.getAttribute('value');
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

  async confrontationSelectLastOption(): Promise<void> {
    await this.confrontationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async confrontationSelectOption(option: string): Promise<void> {
    await this.confrontationSelect.sendKeys(option);
  }

  getConfrontationSelect(): ElementFinder {
    return this.confrontationSelect;
  }

  async getConfrontationSelectedOption(): Promise<string> {
    return await this.confrontationSelect.element(by.css('option:checked')).getText();
  }

  async poulesSelectLastOption(): Promise<void> {
    await this.poulesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async poulesSelectOption(option: string): Promise<void> {
    await this.poulesSelect.sendKeys(option);
  }

  getPoulesSelect(): ElementFinder {
    return this.poulesSelect;
  }

  async getPoulesSelectedOption(): Promise<string> {
    return await this.poulesSelect.element(by.css('option:checked')).getText();
  }

  async delegationSelectLastOption(): Promise<void> {
    await this.delegationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async delegationSelectOption(option: string): Promise<void> {
    await this.delegationSelect.sendKeys(option);
  }

  getDelegationSelect(): ElementFinder {
    return this.delegationSelect;
  }

  async getDelegationSelectedOption(): Promise<string> {
    return await this.delegationSelect.element(by.css('option:checked')).getText();
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

export class TeamDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-team-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-team'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
