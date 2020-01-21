import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KidoleTestModule } from '../../../test.module';
import { OptionsDetailComponent } from 'app/entities/options/options-detail.component';
import { Options } from 'app/shared/model/options.model';

describe('Component Tests', () => {
  describe('Options Management Detail Component', () => {
    let comp: OptionsDetailComponent;
    let fixture: ComponentFixture<OptionsDetailComponent>;
    const route = ({ data: of({ options: new Options(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [OptionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OptionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load options on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.options).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
