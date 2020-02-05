import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';

@Component({
  templateUrl: './phase-delete-dialog.component.html'
})
export class PhaseDeleteDialogComponent {
  phase?: IPhase;

  constructor(protected phaseService: PhaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('phaseListModification');
      this.activeModal.close();
    });
  }
}
