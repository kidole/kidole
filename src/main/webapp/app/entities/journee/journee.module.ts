import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { JourneeComponent } from './journee.component';
import { JourneeDetailComponent } from './journee-detail.component';
import { JourneeUpdateComponent } from './journee-update.component';
import { JourneeDeleteDialogComponent } from './journee-delete-dialog.component';
import { journeeRoute } from './journee.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(journeeRoute)],
  declarations: [JourneeComponent, JourneeDetailComponent, JourneeUpdateComponent, JourneeDeleteDialogComponent],
  entryComponents: [JourneeDeleteDialogComponent]
})
export class KidoleJourneeModule {}
