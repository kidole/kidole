import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { PhaseComponent } from 'app/entities/phase/phase.component';
import { PhaseService } from 'app/entities/phase/phase.service';
import { Phase } from 'app/shared/model/phase.model';

describe('Component Tests', () => {
  describe('Phase Management Component', () => {
    let comp: PhaseComponent;
    let fixture: ComponentFixture<PhaseComponent>;
    let service: PhaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PhaseComponent],
        providers: []
      })
        .overrideTemplate(PhaseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhaseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhaseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Phase(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.phases && comp.phases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
