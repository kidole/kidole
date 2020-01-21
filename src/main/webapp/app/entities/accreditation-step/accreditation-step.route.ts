import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccreditationStep, AccreditationStep } from 'app/shared/model/accreditation-step.model';
import { AccreditationStepService } from './accreditation-step.service';
import { AccreditationStepComponent } from './accreditation-step.component';
import { AccreditationStepDetailComponent } from './accreditation-step-detail.component';
import { AccreditationStepUpdateComponent } from './accreditation-step-update.component';

@Injectable({ providedIn: 'root' })
export class AccreditationStepResolve implements Resolve<IAccreditationStep> {
  constructor(private service: AccreditationStepService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccreditationStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accreditationStep: HttpResponse<AccreditationStep>) => {
          if (accreditationStep.body) {
            return of(accreditationStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccreditationStep());
  }
}

export const accreditationStepRoute: Routes = [
  {
    path: '',
    component: AccreditationStepComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditationStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccreditationStepDetailComponent,
    resolve: {
      accreditationStep: AccreditationStepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditationStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccreditationStepUpdateComponent,
    resolve: {
      accreditationStep: AccreditationStepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditationStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccreditationStepUpdateComponent,
    resolve: {
      accreditationStep: AccreditationStepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.accreditationStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
