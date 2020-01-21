import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { DelegationMembersDetailComponent } from 'app/entities/delegation-members/delegation-members-detail.component';
import { DelegationMembers } from 'app/shared/model/delegation-members.model';

describe('Component Tests', () => {
  describe('DelegationMembers Management Detail Component', () => {
    let comp: DelegationMembersDetailComponent;
    let fixture: ComponentFixture<DelegationMembersDetailComponent>;
    const route = ({ data: of({ delegationMembers: new DelegationMembers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [DelegationMembersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DelegationMembersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DelegationMembersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load delegationMembers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.delegationMembers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
