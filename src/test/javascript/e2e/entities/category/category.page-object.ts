import { element, by, ElementFinder } from 'protractor';

export class CategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-category div table .btn-danger'));
  title = element.all(by.css('jhi-category div h2#page-heading span')).first();

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

export class CategoryUpdatePage {
  pageTitle = element(by.id('jhi-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  categoryNameInput = element(by.id('field_categoryName'));
  yearlimitInput = element(by.id('field_yearlimit'));
  teamLimitNumbInput = element(by.id('field_teamLimitNumb'));
  participantLimitByteamInput = element(by.id('field_participantLimitByteam'));
  categoryRuleInput = element(by.id('field_categoryRule'));
  disciplineSelect = element(by.id('field_discipline'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCategoryNameInput(categoryName: string): Promise<void> {
    await this.categoryNameInput.sendKeys(categoryName);
  }

  async getCategoryNameInput(): Promise<string> {
    return await this.categoryNameInput.getAttribute('value');
  }

  async setYearlimitInput(yearlimit: string): Promise<void> {
    await this.yearlimitInput.sendKeys(yearlimit);
  }

  async getYearlimitInput(): Promise<string> {
    return await this.yearlimitInput.getAttribute('value');
  }

  async setTeamLimitNumbInput(teamLimitNumb: string): Promise<void> {
    await this.teamLimitNumbInput.sendKeys(teamLimitNumb);
  }

  async getTeamLimitNumbInput(): Promise<string> {
    return await this.teamLimitNumbInput.getAttribute('value');
  }

  async setParticipantLimitByteamInput(participantLimitByteam: string): Promise<void> {
    await this.participantLimitByteamInput.sendKeys(participantLimitByteam);
  }

  async getParticipantLimitByteamInput(): Promise<string> {
    return await this.participantLimitByteamInput.getAttribute('value');
  }

  async setCategoryRuleInput(categoryRule: string): Promise<void> {
    await this.categoryRuleInput.sendKeys(categoryRule);
  }

  async getCategoryRuleInput(): Promise<string> {
    return await this.categoryRuleInput.getAttribute('value');
  }

  async disciplineSelectLastOption(): Promise<void> {
    await this.disciplineSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async disciplineSelectOption(option: string): Promise<void> {
    await this.disciplineSelect.sendKeys(option);
  }

  getDisciplineSelect(): ElementFinder {
    return this.disciplineSelect;
  }

  async getDisciplineSelectedOption(): Promise<string> {
    return await this.disciplineSelect.element(by.css('option:checked')).getText();
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

export class CategoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-category-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-category'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
