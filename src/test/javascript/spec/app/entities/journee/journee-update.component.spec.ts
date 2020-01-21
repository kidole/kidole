import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { JourneeUpdateComponent } from 'app/entities/journee/journee-update.component';
import { JourneeService } from 'app/entities/journee/journee.service';
import { Journee } from 'app/shared/model/journee.model';

describe('Component Tests', () => {
  describe('Journee Management Update Component', () => {
    let comp: JourneeUpdateComponent;
    let fixture: ComponentFixture<JourneeUpdateComponent>;
    let service: JourneeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [JourneeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JourneeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourneeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourneeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Journee(123);
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
        const entity = new Journee();
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
