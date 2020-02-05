import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJournee, Journee } from 'app/shared/model/journee.model';
import { JourneeService } from './journee.service';
import { JourneeComponent } from './journee.component';
import { JourneeDetailComponent } from './journee-detail.component';
import { JourneeUpdateComponent } from './journee-update.component';

@Injectable({ providedIn: 'root' })
export class JourneeResolve implements Resolve<IJournee> {
  constructor(private service: JourneeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJournee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((journee: HttpResponse<Journee>) => {
          if (journee.body) {
            return of(journee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Journee());
  }
}

export const journeeRoute: Routes = [
  {
    path: '',
    component: JourneeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.journee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JourneeDetailComponent,
    resolve: {
      journee: JourneeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.journee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JourneeUpdateComponent,
    resolve: {
      journee: JourneeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.journee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JourneeUpdateComponent,
    resolve: {
      journee: JourneeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.journee.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
