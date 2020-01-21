import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from './accreditation.service';

@Component({
  templateUrl: './accreditation-delete-dialog.component.html'
})
export class AccreditationDeleteDialogComponent {
  accreditation?: IAccreditation;

  constructor(
    protected accreditationService: AccreditationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accreditationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accreditationListModification');
      this.activeModal.close();
    });
  }
}
