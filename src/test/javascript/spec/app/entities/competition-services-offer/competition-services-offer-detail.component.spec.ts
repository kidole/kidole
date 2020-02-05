import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionServicesOfferDetailComponent } from 'app/entities/competition-services-offer/competition-services-offer-detail.component';
import { CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

describe('Component Tests', () => {
  describe('CompetitionServicesOffer Management Detail Component', () => {
    let comp: CompetitionServicesOfferDetailComponent;
    let fixture: ComponentFixture<CompetitionServicesOfferDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ competitionServicesOffer: new CompetitionServicesOffer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionServicesOfferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompetitionServicesOfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitionServicesOfferDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load competitionServicesOffer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitionServicesOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
