import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { JourneeDetailComponent } from 'app/entities/journee/journee-detail.component';
import { Journee } from 'app/shared/model/journee.model';

describe('Component Tests', () => {
  describe('Journee Management Detail Component', () => {
    let comp: JourneeDetailComponent;
    let fixture: ComponentFixture<JourneeDetailComponent>;
    const route = ({ data: of({ journee: new Journee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [JourneeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JourneeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourneeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load journee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.journee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
