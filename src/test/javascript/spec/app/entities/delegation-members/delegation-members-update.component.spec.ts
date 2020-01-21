import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { DelegationMembersUpdateComponent } from 'app/entities/delegation-members/delegation-members-update.component';
import { DelegationMembersService } from 'app/entities/delegation-members/delegation-members.service';
import { DelegationMembers } from 'app/shared/model/delegation-members.model';

describe('Component Tests', () => {
  describe('DelegationMembers Management Update Component', () => {
    let comp: DelegationMembersUpdateComponent;
    let fixture: ComponentFixture<DelegationMembersUpdateComponent>;
    let service: DelegationMembersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [DelegationMembersUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DelegationMembersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegationMembersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegationMembersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DelegationMembers(123);
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
        const entity = new DelegationMembers();
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
