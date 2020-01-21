import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { CompetitionservicejoinedComponent } from './competitionservicejoined.component';
import { CompetitionservicejoinedDetailComponent } from './competitionservicejoined-detail.component';
import { CompetitionservicejoinedUpdateComponent } from './competitionservicejoined-update.component';
import { CompetitionservicejoinedDeleteDialogComponent } from './competitionservicejoined-delete-dialog.component';
import { competitionservicejoinedRoute } from './competitionservicejoined.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(competitionservicejoinedRoute)],
  declarations: [
    CompetitionservicejoinedComponent,
    CompetitionservicejoinedDetailComponent,
    CompetitionservicejoinedUpdateComponent,
    CompetitionservicejoinedDeleteDialogComponent
  ],
  entryComponents: [CompetitionservicejoinedDeleteDialogComponent]
})
export class KidoleCompetitionservicejoinedModule {}
