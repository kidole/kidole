import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { FormatComponent } from './format.component';
import { FormatDetailComponent } from './format-detail.component';
import { FormatUpdateComponent } from './format-update.component';
import { FormatDeleteDialogComponent } from './format-delete-dialog.component';
import { formatRoute } from './format.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(formatRoute)],
  declarations: [FormatComponent, FormatDetailComponent, FormatUpdateComponent, FormatDeleteDialogComponent],
  entryComponents: [FormatDeleteDialogComponent]
})
export class KidoleFormatModule {}
