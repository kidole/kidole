import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { PoulesDetailComponent } from 'app/entities/poules/poules-detail.component';
import { Poules } from 'app/shared/model/poules.model';

describe('Component Tests', () => {
  describe('Poules Management Detail Component', () => {
    let comp: PoulesDetailComponent;
    let fixture: ComponentFixture<PoulesDetailComponent>;
    const route = ({ data: of({ poules: new Poules(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [PoulesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoulesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoulesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load poules on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poules).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
