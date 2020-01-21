import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { FormatComponent } from 'app/entities/format/format.component';
import { FormatService } from 'app/entities/format/format.service';
import { Format } from 'app/shared/model/format.model';

describe('Component Tests', () => {
  describe('Format Management Component', () => {
    let comp: FormatComponent;
    let fixture: ComponentFixture<FormatComponent>;
    let service: FormatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [FormatComponent],
        providers: []
      })
        .overrideTemplate(FormatComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormatComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormatService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Format(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.formats && comp.formats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
