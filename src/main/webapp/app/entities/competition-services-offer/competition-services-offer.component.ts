import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from './competition-services-offer.service';
import { CompetitionServicesOfferDeleteDialogComponent } from './competition-services-offer-delete-dialog.component';

@Component({
  selector: 'jhi-competition-services-offer',
  templateUrl: './competition-services-offer.component.html'
})
export class CompetitionServicesOfferComponent implements OnInit, OnDestroy {
  competitionServicesOffers?: ICompetitionServicesOffer[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitionServicesOfferService: CompetitionServicesOfferService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitionServicesOfferService.query().subscribe((res: HttpResponse<ICompetitionServicesOffer[]>) => {
      this.competitionServicesOffers = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitionServicesOffers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetitionServicesOffer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetitionServicesOffers(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitionServicesOfferListModification', () => this.loadAll());
  }

  delete(competitionServicesOffer: ICompetitionServicesOffer): void {
    const modalRef = this.modalService.open(CompetitionServicesOfferDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competitionServicesOffer = competitionServicesOffer;
  }
}
