import { element, by, ElementFinder } from 'protractor';

export class CompetitionServicesOfferComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-competition-services-offer div table .btn-danger'));
  title = element.all(by.css('jhi-competition-services-offer div h2#page-heading span')).first();

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

export class CompetitionServicesOfferUpdatePage {
  pageTitle = element(by.id('jhi-competition-services-offer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  competitionServicesOfferNameInput = element(by.id('field_competitionServicesOfferName'));
  competitionServicesOfferDetailInput = element(by.id('field_competitionServicesOfferDetail'));
  competitionServicesOfferUrlInput = element(by.id('field_competitionServicesOfferUrl'));
  rubricSelect = element(by.id('field_rubric'));
  competitionSelect = element(by.id('field_competition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompetitionServicesOfferNameInput(competitionServicesOfferName: string): Promise<void> {
    await this.competitionServicesOfferNameInput.sendKeys(competitionServicesOfferName);
  }

  async getCompetitionServicesOfferNameInput(): Promise<string> {
    return await this.competitionServicesOfferNameInput.getAttribute('value');
  }

  async setCompetitionServicesOfferDetailInput(competitionServicesOfferDetail: string): Promise<void> {
    await this.competitionServicesOfferDetailInput.sendKeys(competitionServicesOfferDetail);
  }

  async getCompetitionServicesOfferDetailInput(): Promise<string> {
    return await this.competitionServicesOfferDetailInput.getAttribute('value');
  }

  async setCompetitionServicesOfferUrlInput(competitionServicesOfferUrl: string): Promise<void> {
    await this.competitionServicesOfferUrlInput.sendKeys(competitionServicesOfferUrl);
  }

  async getCompetitionServicesOfferUrlInput(): Promise<string> {
    return await this.competitionServicesOfferUrlInput.getAttribute('value');
  }

  async rubricSelectLastOption(): Promise<void> {
    await this.rubricSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async rubricSelectOption(option: string): Promise<void> {
    await this.rubricSelect.sendKeys(option);
  }

  getRubricSelect(): ElementFinder {
    return this.rubricSelect;
  }

  async getRubricSelectedOption(): Promise<string> {
    return await this.rubricSelect.element(by.css('option:checked')).getText();
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

export class CompetitionServicesOfferDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-competitionServicesOffer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-competitionServicesOffer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
