import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPoules, Poules } from 'app/shared/model/poules.model';
import { PoulesService } from './poules.service';
import { PoulesComponent } from './poules.component';
import { PoulesDetailComponent } from './poules-detail.component';
import { PoulesUpdateComponent } from './poules-update.component';

@Injectable({ providedIn: 'root' })
export class PoulesResolve implements Resolve<IPoules> {
  constructor(private service: PoulesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoules> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((poules: HttpResponse<Poules>) => {
          if (poules.body) {
            return of(poules.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Poules());
  }
}

export const poulesRoute: Routes = [
  {
    path: '',
    component: PoulesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.poules.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoulesDetailComponent,
    resolve: {
      poules: PoulesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.poules.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoulesUpdateComponent,
    resolve: {
      poules: PoulesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.poules.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoulesUpdateComponent,
    resolve: {
      poules: PoulesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.poules.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
