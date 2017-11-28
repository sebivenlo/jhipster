import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BeverageChart } from './beverage-chart.model';
import { BeverageChartPopupService } from './beverage-chart-popup.service';
import { BeverageChartService } from './beverage-chart.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-beverage-chart-dialog',
    templateUrl: './beverage-chart-dialog.component.html'
})
export class BeverageChartDialogComponent implements OnInit {

    beverageChart: BeverageChart;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private beverageChartService: BeverageChartService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.beverageChart.id !== undefined) {
            this.subscribeToSaveResponse(
                this.beverageChartService.update(this.beverageChart));
        } else {
            this.subscribeToSaveResponse(
                this.beverageChartService.create(this.beverageChart));
        }
    }

    private subscribeToSaveResponse(result: Observable<BeverageChart>) {
        result.subscribe((res: BeverageChart) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BeverageChart) {
        this.eventManager.broadcast({ name: 'beverageChartListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-beverage-chart-popup',
    template: ''
})
export class BeverageChartPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beverageChartPopupService: BeverageChartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.beverageChartPopupService
                    .open(BeverageChartDialogComponent as Component, params['id']);
            } else {
                this.beverageChartPopupService
                    .open(BeverageChartDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
