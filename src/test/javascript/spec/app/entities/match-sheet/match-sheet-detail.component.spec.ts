import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { MatchSheetDetailComponent } from 'app/entities/match-sheet/match-sheet-detail.component';
import { MatchSheet } from 'app/shared/model/match-sheet.model';

describe('Component Tests', () => {
  describe('MatchSheet Management Detail Component', () => {
    let comp: MatchSheetDetailComponent;
    let fixture: ComponentFixture<MatchSheetDetailComponent>;
    const route = ({ data: of({ matchSheet: new MatchSheet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [MatchSheetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MatchSheetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatchSheetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matchSheet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matchSheet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
