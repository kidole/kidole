import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionServicesOfferUpdateComponent } from 'app/entities/competition-services-offer/competition-services-offer-update.component';
import { CompetitionServicesOfferService } from 'app/entities/competition-services-offer/competition-services-offer.service';
import { CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

describe('Component Tests', () => {
  describe('CompetitionServicesOffer Management Update Component', () => {
    let comp: CompetitionServicesOfferUpdateComponent;
    let fixture: ComponentFixture<CompetitionServicesOfferUpdateComponent>;
    let service: CompetitionServicesOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionServicesOfferUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompetitionServicesOfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionServicesOfferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitionServicesOfferService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompetitionServicesOffer(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompetitionServicesOffer();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
