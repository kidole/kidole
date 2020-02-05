import { element, by, ElementFinder } from 'protractor';

export class DisciplineComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-discipline div table .btn-danger'));
  title = element.all(by.css('jhi-discipline div h2#page-heading span')).first();

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

export class DisciplineUpdatePage {
  pageTitle = element(by.id('jhi-discipline-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  disciplineNameInput = element(by.id('field_disciplineName'));
  sexGenderSelect = element(by.id('field_sexGender'));
  competitionSelect = element(by.id('field_competition'));
  phaseSelect = element(by.id('field_phase'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDisciplineNameInput(disciplineName: string): Promise<void> {
    await this.disciplineNameInput.sendKeys(disciplineName);
  }

  async getDisciplineNameInput(): Promise<string> {
    return await this.disciplineNameInput.getAttribute('value');
  }

  async setSexGenderSelect(sexGender: string): Promise<void> {
    await this.sexGenderSelect.sendKeys(sexGender);
  }

  async getSexGenderSelect(): Promise<string> {
    return await this.sexGenderSelect.element(by.css('option:checked')).getText();
  }

  async sexGenderSelectLastOption(): Promise<void> {
    await this.sexGenderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async competitionSelectLastOption(): Promise<void> {
    await this.competitionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async competitionSelectOption(option: string): Promise<void> {
    await this.competitionSelect.sendKeys(option);
  }

  getCompetitionSelect(): ElementFinder {
    return this.competitionSelect;
  }

  async getCompetitionSelectedOption(): Promise<string> {
    return await this.competitionSelect.element(by.css('option:checked')).getText();
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

export class DisciplineDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-discipline-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-discipline'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
