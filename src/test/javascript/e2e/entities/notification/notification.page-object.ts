import { element, by, ElementFinder } from 'protractor';

export class NotificationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-notification div table .btn-danger'));
  title = element.all(by.css('jhi-notification div h2#page-heading span')).first();

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

export class NotificationUpdatePage {
  pageTitle = element(by.id('jhi-notification-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  notificationTitleInput = element(by.id('field_notificationTitle'));
  notificationSubjectInput = element(by.id('field_notificationSubject'));
  notificationUrlInput = element(by.id('field_notificationUrl'));
  notificationImageInput = element(by.id('file_notificationImage'));
  notificationStatusSelect = element(by.id('field_notificationStatus'));
  notificationTypeSelect = element(by.id('field_notificationType'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNotificationTitleInput(notificationTitle: string): Promise<void> {
    await this.notificationTitleInput.sendKeys(notificationTitle);
  }

  async getNotificationTitleInput(): Promise<string> {
    return await this.notificationTitleInput.getAttribute('value');
  }

  async setNotificationSubjectInput(notificationSubject: string): Promise<void> {
    await this.notificationSubjectInput.sendKeys(notificationSubject);
  }

  async getNotificationSubjectInput(): Promise<string> {
    return await this.notificationSubjectInput.getAttribute('value');
  }

  async setNotificationUrlInput(notificationUrl: string): Promise<void> {
    await this.notificationUrlInput.sendKeys(notificationUrl);
  }

  async getNotificationUrlInput(): Promise<string> {
    return await this.notificationUrlInput.getAttribute('value');
  }

  async setNotificationImageInput(notificationImage: string): Promise<void> {
    await this.notificationImageInput.sendKeys(notificationImage);
  }

  async getNotificationImageInput(): Promise<string> {
    return await this.notificationImageInput.getAttribute('value');
  }

  async setNotificationStatusSelect(notificationStatus: string): Promise<void> {
    await this.notificationStatusSelect.sendKeys(notificationStatus);
  }

  async getNotificationStatusSelect(): Promise<string> {
    return await this.notificationStatusSelect.element(by.css('option:checked')).getText();
  }

  async notificationStatusSelectLastOption(): Promise<void> {
    await this.notificationStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setNotificationTypeSelect(notificationType: string): Promise<void> {
    await this.notificationTypeSelect.sendKeys(notificationType);
  }

  async getNotificationTypeSelect(): Promise<string> {
    return await this.notificationTypeSelect.element(by.css('option:checked')).getText();
  }

  async notificationTypeSelectLastOption(): Promise<void> {
    await this.notificationTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class NotificationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notification-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notification'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
