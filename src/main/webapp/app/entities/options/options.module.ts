import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { OptionsComponent } from './options.component';
import { OptionsDetailComponent } from './options-detail.component';
import { OptionsUpdateComponent } from './options-update.component';
import { OptionsDeleteDialogComponent } from './options-delete-dialog.component';
import { optionsRoute } from './options.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(optionsRoute)],
  declarations: [OptionsComponent, OptionsDetailComponent, OptionsUpdateComponent, OptionsDeleteDialogComponent],
  entryComponents: [OptionsDeleteDialogComponent]
})
export class KidoleOptionsModule {}
