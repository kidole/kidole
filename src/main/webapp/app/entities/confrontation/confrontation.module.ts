import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { ConfrontationComponent } from './confrontation.component';
import { ConfrontationDetailComponent } from './confrontation-detail.component';
import { ConfrontationUpdateComponent } from './confrontation-update.component';
import { ConfrontationDeleteDialogComponent } from './confrontation-delete-dialog.component';
import { confrontationRoute } from './confrontation.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(confrontationRoute)],
  declarations: [ConfrontationComponent, ConfrontationDetailComponent, ConfrontationUpdateComponent, ConfrontationDeleteDialogComponent],
  entryComponents: [ConfrontationDeleteDialogComponent]
})
export class KidoleConfrontationModule {}
