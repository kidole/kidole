import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { AccreditationStepComponent } from './accreditation-step.component';
import { AccreditationStepDetailComponent } from './accreditation-step-detail.component';
import { AccreditationStepUpdateComponent } from './accreditation-step-update.component';
import { AccreditationStepDeleteDialogComponent } from './accreditation-step-delete-dialog.component';
import { accreditationStepRoute } from './accreditation-step.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(accreditationStepRoute)],
  declarations: [
    AccreditationStepComponent,
    AccreditationStepDetailComponent,
    AccreditationStepUpdateComponent,
    AccreditationStepDeleteDialogComponent
  ],
  entryComponents: [AccreditationStepDeleteDialogComponent]
})
export class KidoleAccreditationStepModule {}
