import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICompetitionServicesOffer, CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from './competition-services-offer.service';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

type SelectableEntity = IRubrique | ICompetition;

@Component({
  selector: 'jhi-competition-services-offer-update',
  templateUrl: './competition-services-offer-update.component.html'
})
export class CompetitionServicesOfferUpdateComponent implements OnInit {
  isSaving = false;

  rubriques: IRubrique[] = [];

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    details: [],
    url: [],
    rubriqueId: [],
    competitionId: []
  });

  constructor(
    protected competitionServicesOfferService: CompetitionServicesOfferService,
    protected rubriqueService: RubriqueService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionServicesOffer }) => {
      this.updateForm(competitionServicesOffer);

      this.rubriqueService
        .query({ filter: 'competitionservicesoffer-is-null' })
        .pipe(
          map((res: HttpResponse<IRubrique[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IRubrique[]) => {
          if (!competitionServicesOffer.rubriqueId) {
            this.rubriques = resBody;
          } else {
            this.rubriqueService
              .find(competitionServicesOffer.rubriqueId)
              .pipe(
                map((subRes: HttpResponse<IRubrique>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRubrique[]) => {
                this.rubriques = concatRes;
              });
          }
        });

      this.competitionService
        .query()
        .pipe(
          map((res: HttpResponse<ICompetition[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICompetition[]) => (this.competitions = resBody));
    });
  }

  updateForm(competitionServicesOffer: ICompetitionServicesOffer): void {
    this.editForm.patchValue({
      id: competitionServicesOffer.id,
      name: competitionServicesOffer.name,
      details: competitionServicesOffer.details,
      url: competitionServicesOffer.url,
      rubriqueId: competitionServicesOffer.rubriqueId,
      competitionId: competitionServicesOffer.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competitionServicesOffer = this.createFromForm();
    if (competitionServicesOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionServicesOfferService.update(competitionServicesOffer));
    } else {
      this.subscribeToSaveResponse(this.competitionServicesOfferService.create(competitionServicesOffer));
    }
  }

  private createFromForm(): ICompetitionServicesOffer {
    return {
      ...new CompetitionServicesOffer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      details: this.editForm.get(['details'])!.value,
      url: this.editForm.get(['url'])!.value,
      rubriqueId: this.editForm.get(['rubriqueId'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitionServicesOffer>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
