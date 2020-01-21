import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionservicejoinedComponent } from 'app/entities/competitionservicejoined/competitionservicejoined.component';
import { CompetitionservicejoinedService } from 'app/entities/competitionservicejoined/competitionservicejoined.service';
import { Competitionservicejoined } from 'app/shared/model/competitionservicejoined.model';

describe('Component Tests', () => {
  describe('Competitionservicejoined Management Component', () => {
    let comp: CompetitionservicejoinedComponent;
    let fixture: ComponentFixture<CompetitionservicejoinedComponent>;
    let service: CompetitionservicejoinedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionservicejoinedComponent],
        providers: []
      })
        .overrideTemplate(CompetitionservicejoinedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionservicejoinedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitionservicejoinedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Competitionservicejoined(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competitionservicejoineds && comp.competitionservicejoineds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
