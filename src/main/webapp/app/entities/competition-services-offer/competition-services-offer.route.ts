import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompetitionServicesOffer, CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from './competition-services-offer.service';
import { CompetitionServicesOfferComponent } from './competition-services-offer.component';
import { CompetitionServicesOfferDetailComponent } from './competition-services-offer-detail.component';
import { CompetitionServicesOfferUpdateComponent } from './competition-services-offer-update.component';

@Injectable({ providedIn: 'root' })
export class CompetitionServicesOfferResolve implements Resolve<ICompetitionServicesOffer> {
  constructor(private service: CompetitionServicesOfferService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompetitionServicesOffer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((competitionServicesOffer: HttpResponse<CompetitionServicesOffer>) => {
          if (competitionServicesOffer.body) {
            return of(competitionServicesOffer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompetitionServicesOffer());
  }
}

export const competitionServicesOfferRoute: Routes = [
  {
    path: '',
    component: CompetitionServicesOfferComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.competitionServicesOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompetitionServicesOfferDetailComponent,
    resolve: {
      competitionServicesOffer: CompetitionServicesOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.competitionServicesOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompetitionServicesOfferUpdateComponent,
    resolve: {
      competitionServicesOffer: CompetitionServicesOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.competitionServicesOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompetitionServicesOfferUpdateComponent,
    resolve: {
      competitionServicesOffer: CompetitionServicesOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.competitionServicesOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
