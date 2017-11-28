import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyPopupService } from './property-popup.service';
import { PropertyService } from './property.service';
import { Beer, BeerService } from '../beer';
import { Wine, WineService } from '../wine';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-property-dialog',
    templateUrl: './property-dialog.component.html'
})
export class PropertyDialogComponent implements OnInit {

    property: Property;
    isSaving: boolean;

    beers: Beer[];

    wines: Wine[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private propertyService: PropertyService,
        private beerService: BeerService,
        private wineService: WineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.beerService.query()
            .subscribe((res: ResponseWrapper) => { this.beers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.wineService.query()
            .subscribe((res: ResponseWrapper) => { this.wines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(
                this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(
                this.propertyService.create(this.property));
        }
    }

    private subscribeToSaveResponse(result: Observable<Property>) {
        result.subscribe((res: Property) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Property) {
        this.eventManager.broadcast({ name: 'propertyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackBeerById(index: number, item: Beer) {
        return item.id;
    }

    trackWineById(index: number, item: Wine) {
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
    selector: 'jhi-property-popup',
    template: ''
})
export class PropertyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertyPopupService: PropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component, params['id']);
            } else {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
