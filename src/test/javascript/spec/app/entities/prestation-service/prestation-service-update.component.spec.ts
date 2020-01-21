import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { PrestationServiceUpdateComponent } from 'app/entities/prestation-service/prestation-service-update.component';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';
import { PrestationService } from 'app/shared/model/prestation-service.model';

describe('Component Tests', () => {
  describe('PrestationService Management Update Component', () => {
    let comp: PrestationServiceUpdateComponent;
    let fixture: ComponentFixture<PrestationServiceUpdateComponent>;
    let service: PrestationServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PrestationServiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrestationServiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrestationServiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrestationServiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrestationService(123);
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
        const entity = new PrestationService();
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
