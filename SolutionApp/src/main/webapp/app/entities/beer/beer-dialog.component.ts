import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Beer } from './beer.model';
import { BeerPopupService } from './beer-popup.service';
import { BeerService } from './beer.service';
import { BeverageChart, BeverageChartService } from '../beverage-chart';
import { Property, PropertyService } from '../property';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-beer-dialog',
    templateUrl: './beer-dialog.component.html'
})
export class BeerDialogComponent implements OnInit {

    beer: Beer;
    isSaving: boolean;

    beveragecharts: BeverageChart[];

    properties: Property[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private beerService: BeerService,
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
        if (this.beer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.beerService.update(this.beer));
        } else {
            this.subscribeToSaveResponse(
                this.beerService.create(this.beer));
        }
    }

    private subscribeToSaveResponse(result: Observable<Beer>) {
        result.subscribe((res: Beer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Beer) {
        this.eventManager.broadcast({ name: 'beerListModification', content: 'OK'});
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
    selector: 'jhi-beer-popup',
    template: ''
})
export class BeerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beerPopupService: BeerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.beerPopupService
                    .open(BeerDialogComponent as Component, params['id']);
            } else {
                this.beerPopupService
                    .open(BeerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
