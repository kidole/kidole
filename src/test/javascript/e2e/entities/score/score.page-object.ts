import { element, by, ElementFinder } from 'protractor';

export class ScoreComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-score div table .btn-danger'));
  title = element.all(by.css('jhi-score div h2#page-heading span')).first();

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

export class ScoreUpdatePage {
  pageTitle = element(by.id('jhi-score-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  scoreNameInput = element(by.id('field_scoreName'));
  scoreIndexInput = element(by.id('field_scoreIndex'));
  scoreValueInput = element(by.id('field_scoreValue'));
  scoreUnitInput = element(by.id('field_scoreUnit'));
  teamSelect = element(by.id('field_team'));
  confrontationSelect = element(by.id('field_confrontation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setScoreNameInput(scoreName: string): Promise<void> {
    await this.scoreNameInput.sendKeys(scoreName);
  }

  async getScoreNameInput(): Promise<string> {
    return await this.scoreNameInput.getAttribute('value');
  }

  async setScoreIndexInput(scoreIndex: string): Promise<void> {
    await this.scoreIndexInput.sendKeys(scoreIndex);
  }

  async getScoreIndexInput(): Promise<string> {
    return await this.scoreIndexInput.getAttribute('value');
  }

  async setScoreValueInput(scoreValue: string): Promise<void> {
    await this.scoreValueInput.sendKeys(scoreValue);
  }

  async getScoreValueInput(): Promise<string> {
    return await this.scoreValueInput.getAttribute('value');
  }

  async setScoreUnitInput(scoreUnit: string): Promise<void> {
    await this.scoreUnitInput.sendKeys(scoreUnit);
  }

  async getScoreUnitInput(): Promise<string> {
    return await this.scoreUnitInput.getAttribute('value');
  }

  async teamSelectLastOption(): Promise<void> {
    await this.teamSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async teamSelectOption(option: string): Promise<void> {
    await this.teamSelect.sendKeys(option);
  }

  getTeamSelect(): ElementFinder {
    return this.teamSelect;
  }

  async getTeamSelectedOption(): Promise<string> {
    return await this.teamSelect.element(by.css('option:checked')).getText();
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

export class ScoreDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-score-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-score'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
