import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionServicesOfferComponent } from 'app/entities/competition-services-offer/competition-services-offer.component';
import { CompetitionServicesOfferService } from 'app/entities/competition-services-offer/competition-services-offer.service';
import { CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

describe('Component Tests', () => {
  describe('CompetitionServicesOffer Management Component', () => {
    let comp: CompetitionServicesOfferComponent;
    let fixture: ComponentFixture<CompetitionServicesOfferComponent>;
    let service: CompetitionServicesOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionServicesOfferComponent],
        providers: []
      })
        .overrideTemplate(CompetitionServicesOfferComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionServicesOfferComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitionServicesOfferService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompetitionServicesOffer(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competitionServicesOffers && comp.competitionServicesOffers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
