import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionservicejoinedUpdateComponent } from 'app/entities/competitionservicejoined/competitionservicejoined-update.component';
import { CompetitionservicejoinedService } from 'app/entities/competitionservicejoined/competitionservicejoined.service';
import { Competitionservicejoined } from 'app/shared/model/competitionservicejoined.model';

describe('Component Tests', () => {
  describe('Competitionservicejoined Management Update Component', () => {
    let comp: CompetitionservicejoinedUpdateComponent;
    let fixture: ComponentFixture<CompetitionservicejoinedUpdateComponent>;
    let service: CompetitionservicejoinedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionservicejoinedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompetitionservicejoinedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionservicejoinedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitionservicejoinedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Competitionservicejoined(123);
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
        const entity = new Competitionservicejoined();
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
