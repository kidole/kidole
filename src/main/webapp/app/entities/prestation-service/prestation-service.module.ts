import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { PrestationServiceComponent } from './prestation-service.component';
import { PrestationServiceDetailComponent } from './prestation-service-detail.component';
import { PrestationServiceUpdateComponent } from './prestation-service-update.component';
import { PrestationServiceDeleteDialogComponent } from './prestation-service-delete-dialog.component';
import { prestationServiceRoute } from './prestation-service.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(prestationServiceRoute)],
  declarations: [
    PrestationServiceComponent,
    PrestationServiceDetailComponent,
    PrestationServiceUpdateComponent,
    PrestationServiceDeleteDialogComponent
  ],
  entryComponents: [PrestationServiceDeleteDialogComponent]
})
export class KidolePrestationServiceModule {}
