import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { RubriqueComponent } from './rubrique.component';
import { RubriqueDetailComponent } from './rubrique-detail.component';
import { RubriqueUpdateComponent } from './rubrique-update.component';
import { RubriqueDeleteDialogComponent } from './rubrique-delete-dialog.component';
import { rubriqueRoute } from './rubrique.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(rubriqueRoute)],
  declarations: [RubriqueComponent, RubriqueDetailComponent, RubriqueUpdateComponent, RubriqueDeleteDialogComponent],
  entryComponents: [RubriqueDeleteDialogComponent]
})
export class KidoleRubriqueModule {}
