import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { AccreditationComponent } from './accreditation.component';
import { AccreditationDetailComponent } from './accreditation-detail.component';
import { AccreditationUpdateComponent } from './accreditation-update.component';
import { AccreditationDeleteDialogComponent } from './accreditation-delete-dialog.component';
import { accreditationRoute } from './accreditation.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(accreditationRoute)],
  declarations: [AccreditationComponent, AccreditationDetailComponent, AccreditationUpdateComponent, AccreditationDeleteDialogComponent],
  entryComponents: [AccreditationDeleteDialogComponent]
})
export class KidoleAccreditationModule {}
