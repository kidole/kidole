import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionServicesOfferDetailComponent } from 'app/entities/competition-services-offer/competition-services-offer-detail.component';
import { CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

describe('Component Tests', () => {
  describe('CompetitionServicesOffer Management Detail Component', () => {
    let comp: CompetitionServicesOfferDetailComponent;
    let fixture: ComponentFixture<CompetitionServicesOfferDetailComponent>;
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
    });

    describe('OnInit', () => {
      it('Should load competitionServicesOffer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitionServicesOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
