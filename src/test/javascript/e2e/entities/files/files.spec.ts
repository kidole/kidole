import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FilesComponentsPage, FilesDeleteDialog, FilesUpdatePage } from './files.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Files e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let filesComponentsPage: FilesComponentsPage;
  let filesUpdatePage: FilesUpdatePage;
  let filesDeleteDialog: FilesDeleteDialog;
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

  it('should load Files', async () => {
    await navBarPage.goToEntity('files');
    filesComponentsPage = new FilesComponentsPage();
    await browser.wait(ec.visibilityOf(filesComponentsPage.title), 5000);
    expect(await filesComponentsPage.getTitle()).to.eq('kidoleApp.files.home.title');
  });

  it('should load create Files page', async () => {
    await filesComponentsPage.clickOnCreateButton();
    filesUpdatePage = new FilesUpdatePage();
    expect(await filesUpdatePage.getPageTitle()).to.eq('kidoleApp.files.home.createOrEditLabel');
    await filesUpdatePage.cancel();
  });

  it('should create and save Files', async () => {
    const nbButtonsBeforeCreate = await filesComponentsPage.countDeleteButtons();

    await filesComponentsPage.clickOnCreateButton();
    await promise.all([
      filesUpdatePage.setFileNameInput('fileName'),
      filesUpdatePage.setFileTypeInput('fileType'),
      filesUpdatePage.setFileToUploadInput(absolutePath),
      filesUpdatePage.userSelectLastOption(),
      filesUpdatePage.competitionServicesOfferSelectLastOption(),
      filesUpdatePage.prestationServiceSelectLastOption(),
      filesUpdatePage.rubriqueSelectLastOption(),
      filesUpdatePage.competitionSelectLastOption()
    ]);
    expect(await filesUpdatePage.getFileNameInput()).to.eq('fileName', 'Expected FileName value to be equals to fileName');
    expect(await filesUpdatePage.getFileTypeInput()).to.eq('fileType', 'Expected FileType value to be equals to fileType');
    const selectedFilePublic = filesUpdatePage.getFilePublicInput();
    if (await selectedFilePublic.isSelected()) {
      await filesUpdatePage.getFilePublicInput().click();
      expect(await filesUpdatePage.getFilePublicInput().isSelected(), 'Expected filePublic not to be selected').to.be.false;
    } else {
      await filesUpdatePage.getFilePublicInput().click();
      expect(await filesUpdatePage.getFilePublicInput().isSelected(), 'Expected filePublic to be selected').to.be.true;
    }
    expect(await filesUpdatePage.getFileToUploadInput()).to.endsWith(
      fileNameToUpload,
      'Expected FileToUpload value to be end with ' + fileNameToUpload
    );
    await filesUpdatePage.save();
    expect(await filesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await filesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Files', async () => {
    const nbButtonsBeforeDelete = await filesComponentsPage.countDeleteButtons();
    await filesComponentsPage.clickOnLastDeleteButton();

    filesDeleteDialog = new FilesDeleteDialog();
    expect(await filesDeleteDialog.getDialogTitle()).to.eq('kidoleApp.files.delete.question');
    await filesDeleteDialog.clickOnConfirmButton();

    expect(await filesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
