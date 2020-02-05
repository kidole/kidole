import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { PoulesUpdateComponent } from 'app/entities/poules/poules-update.component';
import { PoulesService } from 'app/entities/poules/poules.service';
import { Poules } from 'app/shared/model/poules.model';

describe('Component Tests', () => {
  describe('Poules Management Update Component', () => {
    let comp: PoulesUpdateComponent;
    let fixture: ComponentFixture<PoulesUpdateComponent>;
    let service: PoulesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PoulesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoulesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoulesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoulesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poules(123);
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
        const entity = new Poules();
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
