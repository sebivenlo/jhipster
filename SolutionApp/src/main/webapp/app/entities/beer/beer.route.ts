import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BeerComponent } from './beer.component';
import { BeerDetailComponent } from './beer-detail.component';
import { BeerPopupComponent } from './beer-dialog.component';
import { BeerDeletePopupComponent } from './beer-delete-dialog.component';

export const beerRoute: Routes = [
    {
        path: 'beer',
        component: BeerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'beer/:id',
        component: BeerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beerPopupRoute: Routes = [
    {
        path: 'beer-new',
        component: BeerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beer/:id/edit',
        component: BeerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beer/:id/delete',
        component: BeerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
