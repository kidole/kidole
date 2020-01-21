import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationUpdateComponent } from 'app/entities/accreditation/accreditation-update.component';
import { AccreditationService } from 'app/entities/accreditation/accreditation.service';
import { Accreditation } from 'app/shared/model/accreditation.model';

describe('Component Tests', () => {
  describe('Accreditation Management Update Component', () => {
    let comp: AccreditationUpdateComponent;
    let fixture: ComponentFixture<AccreditationUpdateComponent>;
    let service: AccreditationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccreditationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccreditationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccreditationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Accreditation(123);
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
        const entity = new Accreditation();
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
