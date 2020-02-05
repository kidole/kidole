import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDelegationMembers, DelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from './delegation-members.service';
import { DelegationMembersComponent } from './delegation-members.component';
import { DelegationMembersDetailComponent } from './delegation-members-detail.component';
import { DelegationMembersUpdateComponent } from './delegation-members-update.component';

@Injectable({ providedIn: 'root' })
export class DelegationMembersResolve implements Resolve<IDelegationMembers> {
  constructor(private service: DelegationMembersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDelegationMembers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((delegationMembers: HttpResponse<DelegationMembers>) => {
          if (delegationMembers.body) {
            return of(delegationMembers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DelegationMembers());
  }
}

export const delegationMembersRoute: Routes = [
  {
    path: '',
    component: DelegationMembersComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.delegationMembers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DelegationMembersDetailComponent,
    resolve: {
      delegationMembers: DelegationMembersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.delegationMembers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DelegationMembersUpdateComponent,
    resolve: {
      delegationMembers: DelegationMembersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.delegationMembers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DelegationMembersUpdateComponent,
    resolve: {
      delegationMembers: DelegationMembersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.delegationMembers.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
