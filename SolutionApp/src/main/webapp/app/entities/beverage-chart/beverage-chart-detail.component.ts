import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BeverageChart } from './beverage-chart.model';
import { BeverageChartService } from './beverage-chart.service';

@Component({
    selector: 'jhi-beverage-chart-detail',
    templateUrl: './beverage-chart-detail.component.html'
})
export class BeverageChartDetailComponent implements OnInit, OnDestroy {

    beverageChart: BeverageChart;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private beverageChartService: BeverageChartService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBeverageCharts();
    }

    load(id) {
        this.beverageChartService.find(id).subscribe((beverageChart) => {
            this.beverageChart = beverageChart;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBeverageCharts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'beverageChartListModification',
            (response) => this.load(this.beverageChart.id)
        );
    }
}
