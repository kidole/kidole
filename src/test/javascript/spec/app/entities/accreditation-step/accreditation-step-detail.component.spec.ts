import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationStepDetailComponent } from 'app/entities/accreditation-step/accreditation-step-detail.component';
import { AccreditationStep } from 'app/shared/model/accreditation-step.model';

describe('Component Tests', () => {
  describe('AccreditationStep Management Detail Component', () => {
    let comp: AccreditationStepDetailComponent;
    let fixture: ComponentFixture<AccreditationStepDetailComponent>;
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
    });

    describe('OnInit', () => {
      it('Should load accreditationStep on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accreditationStep).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
