import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { DelegationMembersComponent } from 'app/entities/delegation-members/delegation-members.component';
import { DelegationMembersService } from 'app/entities/delegation-members/delegation-members.service';
import { DelegationMembers } from 'app/shared/model/delegation-members.model';

describe('Component Tests', () => {
  describe('DelegationMembers Management Component', () => {
    let comp: DelegationMembersComponent;
    let fixture: ComponentFixture<DelegationMembersComponent>;
    let service: DelegationMembersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [DelegationMembersComponent],
        providers: []
      })
        .overrideTemplate(DelegationMembersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegationMembersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegationMembersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DelegationMembers(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.delegationMembers && comp.delegationMembers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
