import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AccreditationStepComponentsPage,
  AccreditationStepDeleteDialog,
  AccreditationStepUpdatePage
} from './accreditation-step.page-object';

const expect = chai.expect;

describe('AccreditationStep e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let accreditationStepComponentsPage: AccreditationStepComponentsPage;
  let accreditationStepUpdatePage: AccreditationStepUpdatePage;
  let accreditationStepDeleteDialog: AccreditationStepDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AccreditationSteps', async () => {
    await navBarPage.goToEntity('accreditation-step');
    accreditationStepComponentsPage = new AccreditationStepComponentsPage();
    await browser.wait(ec.visibilityOf(accreditationStepComponentsPage.title), 5000);
    expect(await accreditationStepComponentsPage.getTitle()).to.eq('kidoleApp.accreditationStep.home.title');
  });

  it('should load create AccreditationStep page', async () => {
    await accreditationStepComponentsPage.clickOnCreateButton();
    accreditationStepUpdatePage = new AccreditationStepUpdatePage();
    expect(await accreditationStepUpdatePage.getPageTitle()).to.eq('kidoleApp.accreditationStep.home.createOrEditLabel');
    await accreditationStepUpdatePage.cancel();
  });

  it('should create and save AccreditationSteps', async () => {
    const nbButtonsBeforeCreate = await accreditationStepComponentsPage.countDeleteButtons();

    await accreditationStepComponentsPage.clickOnCreateButton();
    await promise.all([
      accreditationStepUpdatePage.setStartTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      accreditationStepUpdatePage.setEndTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      accreditationStepUpdatePage.setAccreditationStepnumberInput('5'),
      accreditationStepUpdatePage.accreditationTypeSelectLastOption(),
      accreditationStepUpdatePage.setFieldsInput('fields'),
      accreditationStepUpdatePage.competitionSelectLastOption()
    ]);
    expect(await accreditationStepUpdatePage.getStartTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected startTime value to be equals to 2000-12-31'
    );
    expect(await accreditationStepUpdatePage.getEndTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected endTime value to be equals to 2000-12-31'
    );
    expect(await accreditationStepUpdatePage.getAccreditationStepnumberInput()).to.eq(
      '5',
      'Expected accreditationStepnumber value to be equals to 5'
    );
    const selectedIsPublic = accreditationStepUpdatePage.getIsPublicInput();
    if (await selectedIsPublic.isSelected()) {
      await accreditationStepUpdatePage.getIsPublicInput().click();
      expect(await accreditationStepUpdatePage.getIsPublicInput().isSelected(), 'Expected isPublic not to be selected').to.be.false;
    } else {
      await accreditationStepUpdatePage.getIsPublicInput().click();
      expect(await accreditationStepUpdatePage.getIsPublicInput().isSelected(), 'Expected isPublic to be selected').to.be.true;
    }
    const selectedUri = accreditationStepUpdatePage.getUriInput();
    if (await selectedUri.isSelected()) {
      await accreditationStepUpdatePage.getUriInput().click();
      expect(await accreditationStepUpdatePage.getUriInput().isSelected(), 'Expected uri not to be selected').to.be.false;
    } else {
      await accreditationStepUpdatePage.getUriInput().click();
      expect(await accreditationStepUpdatePage.getUriInput().isSelected(), 'Expected uri to be selected').to.be.true;
    }
    expect(await accreditationStepUpdatePage.getFieldsInput()).to.eq('fields', 'Expected Fields value to be equals to fields');
    await accreditationStepUpdatePage.save();
    expect(await accreditationStepUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await accreditationStepComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AccreditationStep', async () => {
    const nbButtonsBeforeDelete = await accreditationStepComponentsPage.countDeleteButtons();
    await accreditationStepComponentsPage.clickOnLastDeleteButton();

    accreditationStepDeleteDialog = new AccreditationStepDeleteDialog();
    expect(await accreditationStepDeleteDialog.getDialogTitle()).to.eq('kidoleApp.accreditationStep.delete.question');
    await accreditationStepDeleteDialog.clickOnConfirmButton();

    expect(await accreditationStepComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
