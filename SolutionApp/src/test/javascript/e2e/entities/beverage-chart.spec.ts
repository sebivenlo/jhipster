import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('BeverageChart e2e test', () => {

    let navBarPage: NavBarPage;
    let beverageChartDialogPage: BeverageChartDialogPage;
    let beverageChartComponentsPage: BeverageChartComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BeverageCharts', () => {
        navBarPage.goToEntity('beverage-chart');
        beverageChartComponentsPage = new BeverageChartComponentsPage();
        expect(beverageChartComponentsPage.getTitle()).toMatch(/jHipsterApp.beverageChart.home.title/);

    });

    it('should load create BeverageChart dialog', () => {
        beverageChartComponentsPage.clickOnCreateButton();
        beverageChartDialogPage = new BeverageChartDialogPage();
        expect(beverageChartDialogPage.getModalTitle()).toMatch(/jHipsterApp.beverageChart.home.createOrEditLabel/);
        beverageChartDialogPage.close();
    });

    it('should create and save BeverageCharts', () => {
        beverageChartComponentsPage.clickOnCreateButton();
        beverageChartDialogPage.setNameInput('name');
        expect(beverageChartDialogPage.getNameInput()).toMatch('name');
        beverageChartDialogPage.setIntroInput('intro');
        expect(beverageChartDialogPage.getIntroInput()).toMatch('intro');
        beverageChartDialogPage.userSelectLastOption();
        beverageChartDialogPage.save();
        expect(beverageChartDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BeverageChartComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-beverage-chart div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BeverageChartDialogPage {
    modalTitle = element(by.css('h4#myBeverageChartLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    introInput = element(by.css('input#field_intro'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setIntroInput = function (intro) {
        this.introInput.sendKeys(intro);
    }

    getIntroInput = function () {
        return this.introInput.getAttribute('value');
    }

    userSelectLastOption = function () {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function (option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function () {
        return this.userSelect;
    }

    getUserSelectedOption = function () {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
