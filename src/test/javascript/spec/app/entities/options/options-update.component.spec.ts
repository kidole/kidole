import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { OptionsUpdateComponent } from 'app/entities/options/options-update.component';
import { OptionsService } from 'app/entities/options/options.service';
import { Options } from 'app/shared/model/options.model';

describe('Component Tests', () => {
  describe('Options Management Update Component', () => {
    let comp: OptionsUpdateComponent;
    let fixture: ComponentFixture<OptionsUpdateComponent>;
    let service: OptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [OptionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Options(123);
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
        const entity = new Options();
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
