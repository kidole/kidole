import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionDetailComponent } from 'app/entities/competition/competition-detail.component';
import { Competition } from 'app/shared/model/competition.model';

describe('Component Tests', () => {
  describe('Competition Management Detail Component', () => {
    let comp: CompetitionDetailComponent;
    let fixture: ComponentFixture<CompetitionDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ competition: new Competition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompetitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitionDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load competition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competition).toEqual(jasmine.objectContaining({ id: 123 }));
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
