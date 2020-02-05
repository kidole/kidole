import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRubrique, Rubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';
import { RubriqueComponent } from './rubrique.component';
import { RubriqueDetailComponent } from './rubrique-detail.component';
import { RubriqueUpdateComponent } from './rubrique-update.component';

@Injectable({ providedIn: 'root' })
export class RubriqueResolve implements Resolve<IRubrique> {
  constructor(private service: RubriqueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRubrique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rubrique: HttpResponse<Rubrique>) => {
          if (rubrique.body) {
            return of(rubrique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rubrique());
  }
}

export const rubriqueRoute: Routes = [
  {
    path: '',
    component: RubriqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.rubrique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RubriqueDetailComponent,
    resolve: {
      rubrique: RubriqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.rubrique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RubriqueUpdateComponent,
    resolve: {
      rubrique: RubriqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.rubrique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RubriqueUpdateComponent,
    resolve: {
      rubrique: RubriqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.rubrique.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
