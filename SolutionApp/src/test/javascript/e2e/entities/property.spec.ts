import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Property e2e test', () => {

    let navBarPage: NavBarPage;
    let propertyDialogPage: PropertyDialogPage;
    let propertyComponentsPage: PropertyComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Properties', () => {
        navBarPage.goToEntity('property');
        propertyComponentsPage = new PropertyComponentsPage();
        expect(propertyComponentsPage.getTitle()).toMatch(/jHipsterApp.property.home.title/);

    });

    it('should load create Property dialog', () => {
        propertyComponentsPage.clickOnCreateButton();
        propertyDialogPage = new PropertyDialogPage();
        expect(propertyDialogPage.getModalTitle()).toMatch(/jHipsterApp.property.home.createOrEditLabel/);
        propertyDialogPage.close();
    });

    it('should create and save Properties', () => {
        propertyComponentsPage.clickOnCreateButton();
        propertyDialogPage.setNameInput('name');
        expect(propertyDialogPage.getNameInput()).toMatch('name');
        propertyDialogPage.save();
        expect(propertyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PropertyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-property div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PropertyDialogPage {
    modalTitle = element(by.css('h4#myPropertyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
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
