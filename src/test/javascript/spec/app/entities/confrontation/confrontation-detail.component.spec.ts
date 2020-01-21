import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { ConfrontationDetailComponent } from 'app/entities/confrontation/confrontation-detail.component';
import { Confrontation } from 'app/shared/model/confrontation.model';

describe('Component Tests', () => {
  describe('Confrontation Management Detail Component', () => {
    let comp: ConfrontationDetailComponent;
    let fixture: ComponentFixture<ConfrontationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ confrontation: new Confrontation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [ConfrontationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConfrontationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConfrontationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load confrontation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.confrontation).toEqual(jasmine.objectContaining({ id: 123 }));
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
