import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { DelegationMembersDetailComponent } from 'app/entities/delegation-members/delegation-members-detail.component';
import { DelegationMembers } from 'app/shared/model/delegation-members.model';

describe('Component Tests', () => {
  describe('DelegationMembers Management Detail Component', () => {
    let comp: DelegationMembersDetailComponent;
    let fixture: ComponentFixture<DelegationMembersDetailComponent>;
    let dataUtils: JhiDataUtils;
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
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load delegationMembers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.delegationMembers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
