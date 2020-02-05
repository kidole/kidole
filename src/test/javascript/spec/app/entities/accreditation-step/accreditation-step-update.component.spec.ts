import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationStepUpdateComponent } from 'app/entities/accreditation-step/accreditation-step-update.component';
import { AccreditationStepService } from 'app/entities/accreditation-step/accreditation-step.service';
import { AccreditationStep } from 'app/shared/model/accreditation-step.model';

describe('Component Tests', () => {
  describe('AccreditationStep Management Update Component', () => {
    let comp: AccreditationStepUpdateComponent;
    let fixture: ComponentFixture<AccreditationStepUpdateComponent>;
    let service: AccreditationStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationStepUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccreditationStepUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccreditationStepUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccreditationStepService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccreditationStep(123);
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
        const entity = new AccreditationStep();
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
