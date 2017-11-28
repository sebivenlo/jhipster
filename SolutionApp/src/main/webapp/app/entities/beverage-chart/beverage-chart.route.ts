import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BeverageChartComponent } from './beverage-chart.component';
import { BeverageChartDetailComponent } from './beverage-chart-detail.component';
import { BeverageChartPopupComponent } from './beverage-chart-dialog.component';
import { BeverageChartDeletePopupComponent } from './beverage-chart-delete-dialog.component';

export const beverageChartRoute: Routes = [
    {
        path: 'beverage-chart',
        component: BeverageChartComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beverageChart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'beverage-chart/:id',
        component: BeverageChartDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beverageChart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beverageChartPopupRoute: Routes = [
    {
        path: 'beverage-chart-new',
        component: BeverageChartPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beverageChart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beverage-chart/:id/edit',
        component: BeverageChartPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beverageChart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beverage-chart/:id/delete',
        component: BeverageChartDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterApp.beverageChart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
