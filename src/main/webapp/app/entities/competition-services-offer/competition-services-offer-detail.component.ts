import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

@Component({
  selector: 'jhi-competition-services-offer-detail',
  templateUrl: './competition-services-offer-detail.component.html'
})
export class CompetitionServicesOfferDetailComponent implements OnInit {
  competitionServicesOffer: ICompetitionServicesOffer | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionServicesOffer }) => {
      this.competitionServicesOffer = competitionServicesOffer;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
