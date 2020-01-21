import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { DelegationMembersComponent } from './delegation-members.component';
import { DelegationMembersDetailComponent } from './delegation-members-detail.component';
import { DelegationMembersUpdateComponent } from './delegation-members-update.component';
import { DelegationMembersDeleteDialogComponent } from './delegation-members-delete-dialog.component';
import { delegationMembersRoute } from './delegation-members.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(delegationMembersRoute)],
  declarations: [
    DelegationMembersComponent,
    DelegationMembersDetailComponent,
    DelegationMembersUpdateComponent,
    DelegationMembersDeleteDialogComponent
  ],
  entryComponents: [DelegationMembersDeleteDialogComponent]
})
export class KidoleDelegationMembersModule {}
