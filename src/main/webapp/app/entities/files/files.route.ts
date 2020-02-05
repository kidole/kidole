import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFiles, Files } from 'app/shared/model/files.model';
import { FilesService } from './files.service';
import { FilesComponent } from './files.component';
import { FilesDetailComponent } from './files-detail.component';
import { FilesUpdateComponent } from './files-update.component';

@Injectable({ providedIn: 'root' })
export class FilesResolve implements Resolve<IFiles> {
  constructor(private service: FilesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFiles> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((files: HttpResponse<Files>) => {
          if (files.body) {
            return of(files.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Files());
  }
}

export const filesRoute: Routes = [
  {
    path: '',
    component: FilesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kidoleApp.files.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FilesDetailComponent,
    resolve: {
      files: FilesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.files.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FilesUpdateComponent,
    resolve: {
      files: FilesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.files.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FilesUpdateComponent,
    resolve: {
      files: FilesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kidoleApp.files.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
