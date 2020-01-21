import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KidoleTestModule } from '../../../test.module';
import { MatchSheetComponent } from 'app/entities/match-sheet/match-sheet.component';
import { MatchSheetService } from 'app/entities/match-sheet/match-sheet.service';
import { MatchSheet } from 'app/shared/model/match-sheet.model';

describe('Component Tests', () => {
  describe('MatchSheet Management Component', () => {
    let comp: MatchSheetComponent;
    let fixture: ComponentFixture<MatchSheetComponent>;
    let service: MatchSheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [MatchSheetComponent],
        providers: []
      })
        .overrideTemplate(MatchSheetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatchSheetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatchSheetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MatchSheet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.matchSheets && comp.matchSheets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
