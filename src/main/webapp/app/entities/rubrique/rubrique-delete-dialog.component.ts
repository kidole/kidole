import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';

@Component({
  templateUrl: './rubrique-delete-dialog.component.html'
})
export class RubriqueDeleteDialogComponent {
  rubrique?: IRubrique;

  constructor(protected rubriqueService: RubriqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rubriqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rubriqueListModification');
      this.activeModal.close();
    });
  }
}
