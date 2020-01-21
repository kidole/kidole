import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { DelegationComponent } from 'app/entities/delegation/delegation.component';
import { DelegationService } from 'app/entities/delegation/delegation.service';
import { Delegation } from 'app/shared/model/delegation.model';

describe('Component Tests', () => {
  describe('Delegation Management Component', () => {
    let comp: DelegationComponent;
    let fixture: ComponentFixture<DelegationComponent>;
    let service: DelegationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [DelegationComponent],
        providers: []
      })
        .overrideTemplate(DelegationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Delegation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.delegations && comp.delegations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
