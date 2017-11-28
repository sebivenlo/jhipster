import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JHipsterAppBeverageChartModule } from './beverage-chart/beverage-chart.module';
import { JHipsterAppBeerModule } from './beer/beer.module';
import { JHipsterAppPropertyModule } from './property/property.module';
import { JHipsterAppWineModule } from './wine/wine.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JHipsterAppBeverageChartModule,
        JHipsterAppBeerModule,
        JHipsterAppPropertyModule,
        JHipsterAppWineModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterAppEntityModule {}
