import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationStepDetailComponent } from 'app/entities/accreditation-step/accreditation-step-detail.component';
import { AccreditationStep } from 'app/shared/model/accreditation-step.model';

describe('Component Tests', () => {
  describe('AccreditationStep Management Detail Component', () => {
    let comp: AccreditationStepDetailComponent;
    let fixture: ComponentFixture<AccreditationStepDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ accreditationStep: new AccreditationStep(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationStepDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccreditationStepDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccreditationStepDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load accreditationStep on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accreditationStep).toEqual(jasmine.objectContaining({ id: 123 }));
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
