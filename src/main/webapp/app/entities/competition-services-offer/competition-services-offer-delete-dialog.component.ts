import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from './competition-services-offer.service';

@Component({
  templateUrl: './competition-services-offer-delete-dialog.component.html'
})
export class CompetitionServicesOfferDeleteDialogComponent {
  competitionServicesOffer?: ICompetitionServicesOffer;

  constructor(
    protected competitionServicesOfferService: CompetitionServicesOfferService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competitionServicesOfferService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competitionServicesOfferListModification');
      this.activeModal.close();
    });
  }
}
