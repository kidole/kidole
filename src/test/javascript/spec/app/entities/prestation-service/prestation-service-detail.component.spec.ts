import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { PrestationServiceDetailComponent } from 'app/entities/prestation-service/prestation-service-detail.component';
import { PrestationService } from 'app/shared/model/prestation-service.model';

describe('Component Tests', () => {
  describe('PrestationService Management Detail Component', () => {
    let comp: PrestationServiceDetailComponent;
    let fixture: ComponentFixture<PrestationServiceDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ prestationService: new PrestationService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PrestationServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrestationServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrestationServiceDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load prestationService on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prestationService).toEqual(jasmine.objectContaining({ id: 123 }));
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
