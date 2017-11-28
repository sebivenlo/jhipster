import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterAppSharedModule } from '../../shared';
import {
    BeerService,
    BeerPopupService,
    BeerComponent,
    BeerDetailComponent,
    BeerDialogComponent,
    BeerPopupComponent,
    BeerDeletePopupComponent,
    BeerDeleteDialogComponent,
    beerRoute,
    beerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...beerRoute,
    ...beerPopupRoute,
];

@NgModule({
    imports: [
        JHipsterAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BeerComponent,
        BeerDetailComponent,
        BeerDialogComponent,
        BeerDeleteDialogComponent,
        BeerPopupComponent,
        BeerDeletePopupComponent,
    ],
    entryComponents: [
        BeerComponent,
        BeerDialogComponent,
        BeerPopupComponent,
        BeerDeleteDialogComponent,
        BeerDeletePopupComponent,
    ],
    providers: [
        BeerService,
        BeerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterAppBeerModule {}
