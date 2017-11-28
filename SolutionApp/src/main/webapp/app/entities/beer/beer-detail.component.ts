import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Beer } from './beer.model';
import { BeerService } from './beer.service';

@Component({
    selector: 'jhi-beer-detail',
    templateUrl: './beer-detail.component.html'
})
export class BeerDetailComponent implements OnInit, OnDestroy {

    beer: Beer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private beerService: BeerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBeers();
    }

    load(id) {
        this.beerService.find(id).subscribe((beer) => {
            this.beer = beer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBeers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'beerListModification',
            (response) => this.load(this.beer.id)
        );
    }
}
