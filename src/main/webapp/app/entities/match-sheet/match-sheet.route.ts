import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatchSheet, MatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from './match-sheet.service';
import { MatchSheetComponent } from './match-sheet.component';
import { MatchSheetDetailComponent } from './match-sheet-detail.component';
import { MatchSheetUpdateComponent } from './match-sheet-update.component';

@Injectable({ providedIn: 'root' })
export class MatchSheetResolve implements Resolve<IMatchSheet> {
  constructor(private service: MatchSheetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatchSheet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matchSheet: HttpResponse<MatchSheet>) => {
          if (matchSheet.body) {
            return of(matchSheet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatchSheet());
  }
}

export const matchSheetRoute: Routes = [
  {
    path: '',
    component: MatchSheetComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.matchSheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatchSheetDetailComponent,
    resolve: {
      matchSheet: MatchSheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.matchSheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatchSheetUpdateComponent,
    resolve: {
      matchSheet: MatchSheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.matchSheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatchSheetUpdateComponent,
    resolve: {
      matchSheet: MatchSheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.matchSheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
