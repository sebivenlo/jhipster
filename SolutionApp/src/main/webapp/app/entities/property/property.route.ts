import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PropertyComponent } from './property.component';
import { PropertyDetailComponent } from './property-detail.component';
import { PropertyPopupComponent } from './property-dialog.component';
import { PropertyDeletePopupComponent } from './property-delete-dialog.component';

export const propertyRoute: Routes = [
    {
        path: 'property',
        component: PropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.property.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'property/:id',
        component: PropertyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.property.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertyPopupRoute: Routes = [
    {
        path: 'property-new',
        component: PropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'property/:id/edit',
        component: PropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'property/:id/delete',
        component: PropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
