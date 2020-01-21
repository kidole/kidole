import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';

@Component({
  templateUrl: './confrontation-delete-dialog.component.html'
})
export class ConfrontationDeleteDialogComponent {
  confrontation?: IConfrontation;

  constructor(
    protected confrontationService: ConfrontationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.confrontationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('confrontationListModification');
      this.activeModal.close();
    });
  }
}
