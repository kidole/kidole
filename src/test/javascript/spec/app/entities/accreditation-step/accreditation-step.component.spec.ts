import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

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
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
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

    it('should load a page', () => {
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
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accreditationSteps && comp.accreditationSteps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
