/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JHipsterAppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BeerDetailComponent } from '../../../../../../main/webapp/app/entities/beer/beer-detail.component';
import { BeerService } from '../../../../../../main/webapp/app/entities/beer/beer.service';
import { Beer } from '../../../../../../main/webapp/app/entities/beer/beer.model';

describe('Component Tests', () => {

    describe('Beer Management Detail Component', () => {
        let comp: BeerDetailComponent;
        let fixture: ComponentFixture<BeerDetailComponent>;
        let service: BeerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterAppTestModule],
                declarations: [BeerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BeerService,
                    JhiEventManager
                ]
            }).overrideTemplate(BeerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Beer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.beer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
