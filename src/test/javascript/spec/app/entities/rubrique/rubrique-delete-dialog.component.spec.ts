import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KidoleTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RubriqueDeleteDialogComponent } from 'app/entities/rubrique/rubrique-delete-dialog.component';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';

describe('Component Tests', () => {
  describe('Rubrique Management Delete Component', () => {
    let comp: RubriqueDeleteDialogComponent;
    let fixture: ComponentFixture<RubriqueDeleteDialogComponent>;
    let service: RubriqueService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KidoleTestModule],
        declarations: [RubriqueDeleteDialogComponent]
      })
        .overrideTemplate(RubriqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RubriqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RubriqueService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
