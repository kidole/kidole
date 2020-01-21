import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { PoulesComponent } from 'app/entities/poules/poules.component';
import { PoulesService } from 'app/entities/poules/poules.service';
import { Poules } from 'app/shared/model/poules.model';

describe('Component Tests', () => {
  describe('Poules Management Component', () => {
    let comp: PoulesComponent;
    let fixture: ComponentFixture<PoulesComponent>;
    let service: PoulesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PoulesComponent],
        providers: []
      })
        .overrideTemplate(PoulesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoulesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoulesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Poules(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.poules && comp.poules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
