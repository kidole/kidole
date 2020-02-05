import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { MatchSheetComponent } from './match-sheet.component';
import { MatchSheetDetailComponent } from './match-sheet-detail.component';
import { MatchSheetUpdateComponent } from './match-sheet-update.component';
import { MatchSheetDeleteDialogComponent } from './match-sheet-delete-dialog.component';
import { matchSheetRoute } from './match-sheet.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(matchSheetRoute)],
  declarations: [MatchSheetComponent, MatchSheetDetailComponent, MatchSheetUpdateComponent, MatchSheetDeleteDialogComponent],
  entryComponents: [MatchSheetDeleteDialogComponent]
})
export class KidoleMatchSheetModule {}
