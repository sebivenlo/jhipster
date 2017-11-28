import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BeverageChart } from './beverage-chart.model';
import { BeverageChartPopupService } from './beverage-chart-popup.service';
import { BeverageChartService } from './beverage-chart.service';

@Component({
    selector: 'jhi-beverage-chart-delete-dialog',
    templateUrl: './beverage-chart-delete-dialog.component.html'
})
export class BeverageChartDeleteDialogComponent {

    beverageChart: BeverageChart;

    constructor(
        private beverageChartService: BeverageChartService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.beverageChartService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'beverageChartListModification',
                content: 'Deleted an beverageChart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-beverage-chart-delete-popup',
    template: ''
})
export class BeverageChartDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beverageChartPopupService: BeverageChartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.beverageChartPopupService
                .open(BeverageChartDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
