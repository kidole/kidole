import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { OptionsComponent } from 'app/entities/options/options.component';
import { OptionsService } from 'app/entities/options/options.service';
import { Options } from 'app/shared/model/options.model';

describe('Component Tests', () => {
  describe('Options Management Component', () => {
    let comp: OptionsComponent;
    let fixture: ComponentFixture<OptionsComponent>;
    let service: OptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [OptionsComponent],
        providers: []
      })
        .overrideTemplate(OptionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Options(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.options && comp.options[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
