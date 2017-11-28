import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Wine } from './wine.model';
import { WinePopupService } from './wine-popup.service';
import { WineService } from './wine.service';
import { BeverageChart, BeverageChartService } from '../beverage-chart';
import { Property, PropertyService } from '../property';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wine-dialog',
    templateUrl: './wine-dialog.component.html'
})
export class WineDialogComponent implements OnInit {

    wine: Wine;
    isSaving: boolean;

    beveragecharts: BeverageChart[];

    properties: Property[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private wineService: WineService,
        private beverageChartService: BeverageChartService,
        private propertyService: PropertyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.beverageChartService.query()
            .subscribe((res: ResponseWrapper) => { this.beveragecharts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.propertyService.query()
            .subscribe((res: ResponseWrapper) => { this.properties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wine.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wineService.update(this.wine));
        } else {
            this.subscribeToSaveResponse(
                this.wineService.create(this.wine));
        }
    }

    private subscribeToSaveResponse(result: Observable<Wine>) {
        result.subscribe((res: Wine) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Wine) {
        this.eventManager.broadcast({ name: 'wineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackBeverageChartById(index: number, item: BeverageChart) {
        return item.id;
    }

    trackPropertyById(index: number, item: Property) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-wine-popup',
    template: ''
})
export class WinePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private winePopupService: WinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.winePopupService
                    .open(WineDialogComponent as Component, params['id']);
            } else {
                this.winePopupService
                    .open(WineDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
