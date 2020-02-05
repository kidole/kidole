import { element, by, ElementFinder } from 'protractor';

export class CompetitionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-competition div table .btn-danger'));
  title = element.all(by.css('jhi-competition div h2#page-heading span')).first();

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

export class CompetitionUpdatePage {
  pageTitle = element(by.id('jhi-competition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  competitionNameInput = element(by.id('field_competitionName'));
  startInput = element(by.id('field_start'));
  endInput = element(by.id('field_end'));
  dateLimitInput = element(by.id('field_dateLimit'));
  detailInput = element(by.id('field_detail'));
  ruleInput = element(by.id('file_rule'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompetitionNameInput(competitionName: string): Promise<void> {
    await this.competitionNameInput.sendKeys(competitionName);
  }

  async getCompetitionNameInput(): Promise<string> {
    return await this.competitionNameInput.getAttribute('value');
  }

  async setStartInput(start: string): Promise<void> {
    await this.startInput.sendKeys(start);
  }

  async getStartInput(): Promise<string> {
    return await this.startInput.getAttribute('value');
  }

  async setEndInput(end: string): Promise<void> {
    await this.endInput.sendKeys(end);
  }

  async getEndInput(): Promise<string> {
    return await this.endInput.getAttribute('value');
  }

  async setDateLimitInput(dateLimit: string): Promise<void> {
    await this.dateLimitInput.sendKeys(dateLimit);
  }

  async getDateLimitInput(): Promise<string> {
    return await this.dateLimitInput.getAttribute('value');
  }

  async setDetailInput(detail: string): Promise<void> {
    await this.detailInput.sendKeys(detail);
  }

  async getDetailInput(): Promise<string> {
    return await this.detailInput.getAttribute('value');
  }

  async setRuleInput(rule: string): Promise<void> {
    await this.ruleInput.sendKeys(rule);
  }

  async getRuleInput(): Promise<string> {
    return await this.ruleInput.getAttribute('value');
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

export class CompetitionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-competition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-competition'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
