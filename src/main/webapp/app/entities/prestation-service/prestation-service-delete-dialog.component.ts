import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from './prestation-service.service';

@Component({
  templateUrl: './prestation-service-delete-dialog.component.html'
})
export class PrestationServiceDeleteDialogComponent {
  prestationService?: IPrestationService;

  constructor(
    protected prestationServiceService: PrestationServiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prestationServiceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prestationServiceListModification');
      this.activeModal.close();
    });
  }
}
