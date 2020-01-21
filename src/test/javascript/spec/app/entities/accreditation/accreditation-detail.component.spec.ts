import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { AccreditationDetailComponent } from 'app/entities/accreditation/accreditation-detail.component';
import { Accreditation } from 'app/shared/model/accreditation.model';

describe('Component Tests', () => {
  describe('Accreditation Management Detail Component', () => {
    let comp: AccreditationDetailComponent;
    let fixture: ComponentFixture<AccreditationDetailComponent>;
    const route = ({ data: of({ accreditation: new Accreditation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [AccreditationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccreditationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccreditationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accreditation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accreditation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
