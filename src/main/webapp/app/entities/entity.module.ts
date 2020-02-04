import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.KidoleProfileModule)
      },
      {
        path: 'competition',
        loadChildren: () => import('./competition/competition.module').then(m => m.KidoleCompetitionModule)
      },
      {
        path: 'journee',
        loadChildren: () => import('./journee/journee.module').then(m => m.KidoleJourneeModule)
      },
      {
        path: 'team',
        loadChildren: () => import('./team/team.module').then(m => m.KidoleTeamModule)
      },
      {
        path: 'confrontation',
        loadChildren: () => import('./confrontation/confrontation.module').then(m => m.KidoleConfrontationModule)
      },
      {
        path: 'score',
        loadChildren: () => import('./score/score.module').then(m => m.KidoleScoreModule)
      },
      {
        path: 'poules',
        loadChildren: () => import('./poules/poules.module').then(m => m.KidolePoulesModule)
      },
      {
        path: 'match-sheet',
        loadChildren: () => import('./match-sheet/match-sheet.module').then(m => m.KidoleMatchSheetModule)
      },
      {
        path: 'discipline',
        loadChildren: () => import('./discipline/discipline.module').then(m => m.KidoleDisciplineModule)
      },
      {
        path: 'options',
        loadChildren: () => import('./options/options.module').then(m => m.KidoleOptionsModule)
      },
      {
        path: 'delegation',
        loadChildren: () => import('./delegation/delegation.module').then(m => m.KidoleDelegationModule)
      },
      {
        path: 'delegation-members',
        loadChildren: () => import('./delegation-members/delegation-members.module').then(m => m.KidoleDelegationMembersModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.KidoleCategoryModule)
      },
      {
        path: 'phase',
        loadChildren: () => import('./phase/phase.module').then(m => m.KidolePhaseModule)
      },
      {
        path: 'localisation',
        loadChildren: () => import('./localisation/localisation.module').then(m => m.KidoleLocalisationModule)
      },
      {
        path: 'format',
        loadChildren: () => import('./format/format.module').then(m => m.KidoleFormatModule)
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.KidoleNotificationModule)
      },
      {
        path: 'prestation-service',
        loadChildren: () => import('./prestation-service/prestation-service.module').then(m => m.KidolePrestationServiceModule)
      },
      {
        path: 'rubrique',
        loadChildren: () => import('./rubrique/rubrique.module').then(m => m.KidoleRubriqueModule)
      },
      {
        path: 'files',
        loadChildren: () => import('./files/files.module').then(m => m.KidoleFilesModule)
      },
      {
        path: 'competition-services-offer',
        loadChildren: () =>
          import('./competition-services-offer/competition-services-offer.module').then(m => m.KidoleCompetitionServicesOfferModule)
      },
      {
        path: 'accreditation',
        loadChildren: () => import('./accreditation/accreditation.module').then(m => m.KidoleAccreditationModule)
      },
      {
        path: 'accreditation-step',
        loadChildren: () => import('./accreditation-step/accreditation-step.module').then(m => m.KidoleAccreditationStepModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class KidoleEntityModule {}
