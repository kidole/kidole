import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccreditation, Accreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from './accreditation.service';
import { AccreditationComponent } from './accreditation.component';
import { AccreditationDetailComponent } from './accreditation-detail.component';
import { AccreditationUpdateComponent } from './accreditation-update.component';

@Injectable({ providedIn: 'root' })
export class AccreditationResolve implements Resolve<IAccreditation> {
  constructor(private service: AccreditationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccreditation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accreditation: HttpResponse<Accreditation>) => {
          if (accreditation.body) {
            return of(accreditation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accreditation());
  }
}

export const accreditationRoute: Routes = [
  {
    path: '',
    component: AccreditationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.accreditation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccreditationDetailComponent,
    resolve: {
      accreditation: AccreditationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccreditationUpdateComponent,
    resolve: {
      accreditation: AccreditationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccreditationUpdateComponent,
    resolve: {
      accreditation: AccreditationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
