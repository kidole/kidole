import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoules } from 'app/shared/model/poules.model';
import { PoulesService } from './poules.service';

@Component({
  templateUrl: './poules-delete-dialog.component.html'
})
export class PoulesDeleteDialogComponent {
  poules?: IPoules;

  constructor(protected poulesService: PoulesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poulesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('poulesListModification');
      this.activeModal.close();
    });
  }
}
