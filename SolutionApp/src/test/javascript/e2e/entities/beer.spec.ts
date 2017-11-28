import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Beer e2e test', () => {

    let navBarPage: NavBarPage;
    let beerDialogPage: BeerDialogPage;
    let beerComponentsPage: BeerComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Beers', () => {
        navBarPage.goToEntity('beer');
        beerComponentsPage = new BeerComponentsPage();
        expect(beerComponentsPage.getTitle()).toMatch(/jHipsterApp.beer.home.title/);

    });

    it('should load create Beer dialog', () => {
        beerComponentsPage.clickOnCreateButton();
        beerDialogPage = new BeerDialogPage();
        expect(beerDialogPage.getModalTitle()).toMatch(/jHipsterApp.beer.home.createOrEditLabel/);
        beerDialogPage.close();
    });

    it('should create and save Beers', () => {
        beerComponentsPage.clickOnCreateButton();
        beerDialogPage.setNameInput('name');
        expect(beerDialogPage.getNameInput()).toMatch('name');
        beerDialogPage.setPriceInput('5');
        expect(beerDialogPage.getPriceInput()).toMatch('5');
        beerDialogPage.setQuantityInput('5');
        expect(beerDialogPage.getQuantityInput()).toMatch('5');
        beerDialogPage.beverageChartSelectLastOption();
        // beerDialogPage.propertySelectLastOption();
        beerDialogPage.save();
        expect(beerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BeerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-beer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BeerDialogPage {
    modalTitle = element(by.css('h4#myBeerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    priceInput = element(by.css('input#field_price'));
    quantityInput = element(by.css('input#field_quantity'));
    beverageChartSelect = element(by.css('select#field_beverageChart'));
    propertySelect = element(by.css('select#field_property'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setPriceInput = function (price) {
        this.priceInput.sendKeys(price);
    }

    getPriceInput = function () {
        return this.priceInput.getAttribute('value');
    }

    setQuantityInput = function (quantity) {
        this.quantityInput.sendKeys(quantity);
    }

    getQuantityInput = function () {
        return this.quantityInput.getAttribute('value');
    }

    beverageChartSelectLastOption = function () {
        this.beverageChartSelect.all(by.tagName('option')).last().click();
    }

    beverageChartSelectOption = function (option) {
        this.beverageChartSelect.sendKeys(option);
    }

    getBeverageChartSelect = function () {
        return this.beverageChartSelect;
    }

    getBeverageChartSelectedOption = function () {
        return this.beverageChartSelect.element(by.css('option:checked')).getText();
    }

    propertySelectLastOption = function () {
        this.propertySelect.all(by.tagName('option')).last().click();
    }

    propertySelectOption = function (option) {
        this.propertySelect.sendKeys(option);
    }

    getPropertySelect = function () {
        return this.propertySelect;
    }

    getPropertySelectedOption = function () {
        return this.propertySelect.element(by.css('option:checked')).getText();
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
