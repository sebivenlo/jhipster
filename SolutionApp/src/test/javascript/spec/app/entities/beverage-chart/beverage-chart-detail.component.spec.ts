/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JHipsterAppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BeverageChartDetailComponent } from '../../../../../../main/webapp/app/entities/beverage-chart/beverage-chart-detail.component';
import { BeverageChartService } from '../../../../../../main/webapp/app/entities/beverage-chart/beverage-chart.service';
import { BeverageChart } from '../../../../../../main/webapp/app/entities/beverage-chart/beverage-chart.model';

describe('Component Tests', () => {

    describe('BeverageChart Management Detail Component', () => {
        let comp: BeverageChartDetailComponent;
        let fixture: ComponentFixture<BeverageChartDetailComponent>;
        let service: BeverageChartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterAppTestModule],
                declarations: [BeverageChartDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BeverageChartService,
                    JhiEventManager
                ]
            }).overrideTemplate(BeverageChartDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeverageChartDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeverageChartService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BeverageChart(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.beverageChart).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
