import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KidoleSharedModule } from 'app/shared/shared.module';
import { CompetitionServicesOfferComponent } from './competition-services-offer.component';
import { CompetitionServicesOfferDetailComponent } from './competition-services-offer-detail.component';
import { CompetitionServicesOfferUpdateComponent } from './competition-services-offer-update.component';
import { CompetitionServicesOfferDeleteDialogComponent } from './competition-services-offer-delete-dialog.component';
import { competitionServicesOfferRoute } from './competition-services-offer.route';

@NgModule({
  imports: [KidoleSharedModule, RouterModule.forChild(competitionServicesOfferRoute)],
  declarations: [
    CompetitionServicesOfferComponent,
    CompetitionServicesOfferDetailComponent,
    CompetitionServicesOfferUpdateComponent,
    CompetitionServicesOfferDeleteDialogComponent
  ],
  entryComponents: [CompetitionServicesOfferDeleteDialogComponent]
})
export class KidoleCompetitionServicesOfferModule {}
