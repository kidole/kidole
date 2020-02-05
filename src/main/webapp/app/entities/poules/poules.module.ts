import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { PoulesComponent } from './poules.component';
import { PoulesDetailComponent } from './poules-detail.component';
import { PoulesUpdateComponent } from './poules-update.component';
import { PoulesDeleteDialogComponent } from './poules-delete-dialog.component';
import { poulesRoute } from './poules.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(poulesRoute)],
  declarations: [PoulesComponent, PoulesDetailComponent, PoulesUpdateComponent, PoulesDeleteDialogComponent],
  entryComponents: [PoulesDeleteDialogComponent]
})
export class KidolePoulesModule {}
