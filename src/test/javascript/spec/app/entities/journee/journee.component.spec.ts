import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { JourneeComponent } from 'app/entities/journee/journee.component';
import { JourneeService } from 'app/entities/journee/journee.service';
import { Journee } from 'app/shared/model/journee.model';

describe('Component Tests', () => {
  describe('Journee Management Component', () => {
    let comp: JourneeComponent;
    let fixture: ComponentFixture<JourneeComponent>;
    let service: JourneeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [JourneeComponent],
        providers: []
      })
        .overrideTemplate(JourneeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourneeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourneeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Journee(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.journees && comp.journees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
