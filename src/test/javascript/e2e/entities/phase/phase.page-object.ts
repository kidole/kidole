import { element, by, ElementFinder } from 'protractor';

export class PhaseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-phase div table .btn-danger'));
  title = element.all(by.css('jhi-phase div h2#page-heading span')).first();

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

export class PhaseUpdatePage {
  pageTitle = element(by.id('jhi-phase-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  phaseNameInput = element(by.id('field_phaseName'));
  phaseNumberInput = element(by.id('field_phaseNumber'));
  phaseDayNumberInput = element(by.id('field_phaseDayNumber'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPhaseNameInput(phaseName: string): Promise<void> {
    await this.phaseNameInput.sendKeys(phaseName);
  }

  async getPhaseNameInput(): Promise<string> {
    return await this.phaseNameInput.getAttribute('value');
  }

  async setPhaseNumberInput(phaseNumber: string): Promise<void> {
    await this.phaseNumberInput.sendKeys(phaseNumber);
  }

  async getPhaseNumberInput(): Promise<string> {
    return await this.phaseNumberInput.getAttribute('value');
  }

  async setPhaseDayNumberInput(phaseDayNumber: string): Promise<void> {
    await this.phaseDayNumberInput.sendKeys(phaseDayNumber);
  }

  async getPhaseDayNumberInput(): Promise<string> {
    return await this.phaseDayNumberInput.getAttribute('value');
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

export class PhaseDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-phase-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-phase'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
