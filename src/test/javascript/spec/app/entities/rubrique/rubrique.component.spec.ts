import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { RubriqueComponent } from 'app/entities/rubrique/rubrique.component';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';
import { Rubrique } from 'app/shared/model/rubrique.model';

describe('Component Tests', () => {
  describe('Rubrique Management Component', () => {
    let comp: RubriqueComponent;
    let fixture: ComponentFixture<RubriqueComponent>;
    let service: RubriqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [RubriqueComponent],
        providers: []
      })
        .overrideTemplate(RubriqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RubriqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RubriqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Rubrique(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rubriques && comp.rubriques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
