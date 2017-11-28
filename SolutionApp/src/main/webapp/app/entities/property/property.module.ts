import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterAppSharedModule } from '../../shared';
import {
    PropertyService,
    PropertyPopupService,
    PropertyComponent,
    PropertyDetailComponent,
    PropertyDialogComponent,
    PropertyPopupComponent,
    PropertyDeletePopupComponent,
    PropertyDeleteDialogComponent,
    propertyRoute,
    propertyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...propertyRoute,
    ...propertyPopupRoute,
];

@NgModule({
    imports: [
        JHipsterAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PropertyComponent,
        PropertyDetailComponent,
        PropertyDialogComponent,
        PropertyDeleteDialogComponent,
        PropertyPopupComponent,
        PropertyDeletePopupComponent,
    ],
    entryComponents: [
        PropertyComponent,
        PropertyDialogComponent,
        PropertyPopupComponent,
        PropertyDeleteDialogComponent,
        PropertyDeletePopupComponent,
    ],
    providers: [
        PropertyService,
        PropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterAppPropertyModule {}
