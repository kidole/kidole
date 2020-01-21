import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJournee } from 'app/shared/model/journee.model';
import { JourneeService } from './journee.service';

@Component({
  templateUrl: './journee-delete-dialog.component.html'
})
export class JourneeDeleteDialogComponent {
  journee?: IJournee;

  constructor(protected journeeService: JourneeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('journeeListModification');
      this.activeModal.close();
    });
  }
}
