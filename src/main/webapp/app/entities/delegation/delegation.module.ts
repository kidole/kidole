import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { DelegationComponent } from './delegation.component';
import { DelegationDetailComponent } from './delegation-detail.component';
import { DelegationUpdateComponent } from './delegation-update.component';
import { DelegationDeleteDialogComponent } from './delegation-delete-dialog.component';
import { delegationRoute } from './delegation.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(delegationRoute)],
  declarations: [DelegationComponent, DelegationDetailComponent, DelegationUpdateComponent, DelegationDeleteDialogComponent],
  entryComponents: [DelegationDeleteDialogComponent]
})
export class KidoleDelegationModule {}
