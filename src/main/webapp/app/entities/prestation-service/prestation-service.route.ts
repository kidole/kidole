import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrestationService, PrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from './prestation-service.service';
import { PrestationServiceComponent } from './prestation-service.component';
import { PrestationServiceDetailComponent } from './prestation-service-detail.component';
import { PrestationServiceUpdateComponent } from './prestation-service-update.component';

@Injectable({ providedIn: 'root' })
export class PrestationServiceResolve implements Resolve<IPrestationService> {
  constructor(private service: PrestationServiceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrestationService> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prestationService: HttpResponse<PrestationService>) => {
          if (prestationService.body) {
            return of(prestationService.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PrestationService());
  }
}

export const prestationServiceRoute: Routes = [
  {
    path: '',
    component: PrestationServiceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.prestationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrestationServiceDetailComponent,
    resolve: {
      prestationService: PrestationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.prestationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrestationServiceUpdateComponent,
    resolve: {
      prestationService: PrestationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.prestationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrestationServiceUpdateComponent,
    resolve: {
      prestationService: PrestationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.prestationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
