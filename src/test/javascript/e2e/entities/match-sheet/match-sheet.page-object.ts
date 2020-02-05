import { element, by, ElementFinder } from 'protractor';

export class MatchSheetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-match-sheet div table .btn-danger'));
  title = element.all(by.css('jhi-match-sheet div h2#page-heading span')).first();

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

export class MatchSheetUpdatePage {
  pageTitle = element(by.id('jhi-match-sheet-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  matchSheetNameInput = element(by.id('field_matchSheetName'));
  matchSheetResumeInput = element(by.id('field_matchSheetResume'));
  isfirstInput = element(by.id('field_isfirst'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMatchSheetNameInput(matchSheetName: string): Promise<void> {
    await this.matchSheetNameInput.sendKeys(matchSheetName);
  }

  async getMatchSheetNameInput(): Promise<string> {
    return await this.matchSheetNameInput.getAttribute('value');
  }

  async setMatchSheetResumeInput(matchSheetResume: string): Promise<void> {
    await this.matchSheetResumeInput.sendKeys(matchSheetResume);
  }

  async getMatchSheetResumeInput(): Promise<string> {
    return await this.matchSheetResumeInput.getAttribute('value');
  }

  getIsfirstInput(): ElementFinder {
    return this.isfirstInput;
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

export class MatchSheetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-matchSheet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-matchSheet'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
