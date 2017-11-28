import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BeverageChart } from './beverage-chart.model';
import { BeverageChartService } from './beverage-chart.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-beverage-chart',
    templateUrl: './beverage-chart.component.html'
})
export class BeverageChartComponent implements OnInit, OnDestroy {
beverageCharts: BeverageChart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private beverageChartService: BeverageChartService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.beverageChartService.query().subscribe(
            (res: ResponseWrapper) => {
                this.beverageCharts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBeverageCharts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BeverageChart) {
        return item.id;
    }
    registerChangeInBeverageCharts() {
        this.eventSubscriber = this.eventManager.subscribe('beverageChartListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
