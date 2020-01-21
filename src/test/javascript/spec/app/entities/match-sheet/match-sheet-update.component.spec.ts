import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { MatchSheetUpdateComponent } from 'app/entities/match-sheet/match-sheet-update.component';
import { MatchSheetService } from 'app/entities/match-sheet/match-sheet.service';
import { MatchSheet } from 'app/shared/model/match-sheet.model';

describe('Component Tests', () => {
  describe('MatchSheet Management Update Component', () => {
    let comp: MatchSheetUpdateComponent;
    let fixture: ComponentFixture<MatchSheetUpdateComponent>;
    let service: MatchSheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [MatchSheetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MatchSheetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatchSheetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatchSheetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatchSheet(123);
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
        const entity = new MatchSheet();
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
