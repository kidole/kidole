import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';
import { AccreditationStepService } from './accreditation-step.service';

@Component({
  templateUrl: './accreditation-step-delete-dialog.component.html'
})
export class AccreditationStepDeleteDialogComponent {
  accreditationStep?: IAccreditationStep;

  constructor(
    protected accreditationStepService: AccreditationStepService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accreditationStepService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accreditationStepListModification');
      this.activeModal.close();
    });
  }
}
