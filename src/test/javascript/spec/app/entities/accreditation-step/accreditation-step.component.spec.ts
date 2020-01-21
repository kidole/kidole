import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationStepComponent } from 'app/entities/accreditation-step/accreditation-step.component';
import { AccreditationStepService } from 'app/entities/accreditation-step/accreditation-step.service';
import { AccreditationStep } from 'app/shared/model/accreditation-step.model';

describe('Component Tests', () => {
  describe('AccreditationStep Management Component', () => {
    let comp: AccreditationStepComponent;
    let fixture: ComponentFixture<AccreditationStepComponent>;
    let service: AccreditationStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationStepComponent],
        providers: []
      })
        .overrideTemplate(AccreditationStepComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccreditationStepComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccreditationStepService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccreditationStep(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accreditationSteps && comp.accreditationSteps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
