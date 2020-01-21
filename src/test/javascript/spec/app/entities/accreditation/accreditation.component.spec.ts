import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationComponent } from 'app/entities/accreditation/accreditation.component';
import { AccreditationService } from 'app/entities/accreditation/accreditation.service';
import { Accreditation } from 'app/shared/model/accreditation.model';

describe('Component Tests', () => {
  describe('Accreditation Management Component', () => {
    let comp: AccreditationComponent;
    let fixture: ComponentFixture<AccreditationComponent>;
    let service: AccreditationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationComponent],
        providers: []
      })
        .overrideTemplate(AccreditationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccreditationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccreditationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Accreditation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accreditations && comp.accreditations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
