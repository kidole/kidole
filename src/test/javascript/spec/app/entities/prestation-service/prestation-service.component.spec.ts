import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { PrestationServiceComponent } from 'app/entities/prestation-service/prestation-service.component';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';
import { PrestationService } from 'app/shared/model/prestation-service.model';

describe('Component Tests', () => {
  describe('PrestationService Management Component', () => {
    let comp: PrestationServiceComponent;
    let fixture: ComponentFixture<PrestationServiceComponent>;
    let service: PrestationServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PrestationServiceComponent],
        providers: []
      })
        .overrideTemplate(PrestationServiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrestationServiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrestationServiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrestationService(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prestationServices && comp.prestationServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
