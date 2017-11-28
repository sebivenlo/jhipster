import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterAppSharedModule } from '../../shared';
import { JHipsterAppAdminModule } from '../../admin/admin.module';
import {
    BeverageChartService,
    BeverageChartPopupService,
    BeverageChartComponent,
    BeverageChartDetailComponent,
    BeverageChartDialogComponent,
    BeverageChartPopupComponent,
    BeverageChartDeletePopupComponent,
    BeverageChartDeleteDialogComponent,
    beverageChartRoute,
    beverageChartPopupRoute,
} from './';

const ENTITY_STATES = [
    ...beverageChartRoute,
    ...beverageChartPopupRoute,
];

@NgModule({
    imports: [
        JHipsterAppSharedModule,
        JHipsterAppAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BeverageChartComponent,
        BeverageChartDetailComponent,
        BeverageChartDialogComponent,
        BeverageChartDeleteDialogComponent,
        BeverageChartPopupComponent,
        BeverageChartDeletePopupComponent,
    ],
    entryComponents: [
        BeverageChartComponent,
        BeverageChartDialogComponent,
        BeverageChartPopupComponent,
        BeverageChartDeleteDialogComponent,
        BeverageChartDeletePopupComponent,
    ],
    providers: [
        BeverageChartService,
        BeverageChartPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterAppBeverageChartModule {}
