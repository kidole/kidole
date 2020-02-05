import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotificationComponentsPage, NotificationDeleteDialog, NotificationUpdatePage } from './notification.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Notification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificationComponentsPage: NotificationComponentsPage;
  let notificationUpdatePage: NotificationUpdatePage;
  let notificationDeleteDialog: NotificationDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Notifications', async () => {
    await navBarPage.goToEntity('notification');
    notificationComponentsPage = new NotificationComponentsPage();
    await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);
    expect(await notificationComponentsPage.getTitle()).to.eq('kidoleApp.notification.home.title');
  });

  it('should load create Notification page', async () => {
    await notificationComponentsPage.clickOnCreateButton();
    notificationUpdatePage = new NotificationUpdatePage();
    expect(await notificationUpdatePage.getPageTitle()).to.eq('kidoleApp.notification.home.createOrEditLabel');
    await notificationUpdatePage.cancel();
  });

  it('should create and save Notifications', async () => {
    const nbButtonsBeforeCreate = await notificationComponentsPage.countDeleteButtons();

    await notificationComponentsPage.clickOnCreateButton();
    await promise.all([
      notificationUpdatePage.setNotificationTitleInput('notificationTitle'),
      notificationUpdatePage.setNotificationSubjectInput('notificationSubject'),
      notificationUpdatePage.setNotificationUrlInput('notificationUrl'),
      notificationUpdatePage.setNotificationImageInput(absolutePath),
      notificationUpdatePage.notificationStatusSelectLastOption(),
      notificationUpdatePage.notificationTypeSelectLastOption(),
      notificationUpdatePage.userSelectLastOption()
    ]);
    expect(await notificationUpdatePage.getNotificationTitleInput()).to.eq(
      'notificationTitle',
      'Expected NotificationTitle value to be equals to notificationTitle'
    );
    expect(await notificationUpdatePage.getNotificationSubjectInput()).to.eq(
      'notificationSubject',
      'Expected NotificationSubject value to be equals to notificationSubject'
    );
    expect(await notificationUpdatePage.getNotificationUrlInput()).to.eq(
      'notificationUrl',
      'Expected NotificationUrl value to be equals to notificationUrl'
    );
    expect(await notificationUpdatePage.getNotificationImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected NotificationImage value to be end with ' + fileNameToUpload
    );
    await notificationUpdatePage.save();
    expect(await notificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Notification', async () => {
    const nbButtonsBeforeDelete = await notificationComponentsPage.countDeleteButtons();
    await notificationComponentsPage.clickOnLastDeleteButton();

    notificationDeleteDialog = new NotificationDeleteDialog();
    expect(await notificationDeleteDialog.getDialogTitle()).to.eq('kidoleApp.notification.delete.question');
    await notificationDeleteDialog.clickOnConfirmButton();

    expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
