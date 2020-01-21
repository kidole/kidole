import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

@Component({
  selector: 'jhi-competition-services-offer-detail',
  templateUrl: './competition-services-offer-detail.component.html'
})
export class CompetitionServicesOfferDetailComponent implements OnInit {
  competitionServicesOffer: ICompetitionServicesOffer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionServicesOffer }) => {
      this.competitionServicesOffer = competitionServicesOffer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
