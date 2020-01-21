import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { CompetitionservicejoinedDetailComponent } from 'app/entities/competitionservicejoined/competitionservicejoined-detail.component';
import { Competitionservicejoined } from 'app/shared/model/competitionservicejoined.model';

describe('Component Tests', () => {
  describe('Competitionservicejoined Management Detail Component', () => {
    let comp: CompetitionservicejoinedDetailComponent;
    let fixture: ComponentFixture<CompetitionservicejoinedDetailComponent>;
    const route = ({ data: of({ competitionservicejoined: new Competitionservicejoined(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [CompetitionservicejoinedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompetitionservicejoinedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitionservicejoinedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load competitionservicejoined on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitionservicejoined).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
