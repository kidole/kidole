import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILocalisation, Localisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';
import { IPrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';

type SelectableEntity = ICompetition | IPrestationService;

@Component({
  selector: 'jhi-localisation-update',
  templateUrl: './localisation-update.component.html'
})
export class LocalisationUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  prestationservices: IPrestationService[] = [];

  editForm = this.fb.group({
    id: [],
    localisationName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    localisationLatitude: [null, [Validators.required]],
    localisationLongitude: [null, [Validators.required]],
    localisationCountry: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    localisationTown: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    localisationRegion: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    localisationLocality: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    isSite: [null, [Validators.required]],
    competitionId: [],
    prestationServiceId: []
  });

  constructor(
    protected localisationService: LocalisationService,
    protected competitionService: CompetitionService,
    protected prestationServiceService: PrestationServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localisation }) => {
      this.updateForm(localisation);

      this.competitionService
        .query()
        .pipe(
          map((res: HttpResponse<ICompetition[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICompetition[]) => (this.competitions = resBody));

      this.prestationServiceService
        .query()
        .pipe(
          map((res: HttpResponse<IPrestationService[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPrestationService[]) => (this.prestationservices = resBody));
    });
  }

  updateForm(localisation: ILocalisation): void {
    this.editForm.patchValue({
      id: localisation.id,
      localisationName: localisation.localisationName,
      localisationLatitude: localisation.localisationLatitude,
      localisationLongitude: localisation.localisationLongitude,
      localisationCountry: localisation.localisationCountry,
      localisationTown: localisation.localisationTown,
      localisationRegion: localisation.localisationRegion,
      localisationLocality: localisation.localisationLocality,
      isSite: localisation.isSite,
      competitionId: localisation.competitionId,
      prestationServiceId: localisation.prestationServiceId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localisation = this.createFromForm();
    if (localisation.id !== undefined) {
      this.subscribeToSaveResponse(this.localisationService.update(localisation));
    } else {
      this.subscribeToSaveResponse(this.localisationService.create(localisation));
    }
  }

  private createFromForm(): ILocalisation {
    return {
      ...new Localisation(),
      id: this.editForm.get(['id'])!.value,
      localisationName: this.editForm.get(['localisationName'])!.value,
      localisationLatitude: this.editForm.get(['localisationLatitude'])!.value,
      localisationLongitude: this.editForm.get(['localisationLongitude'])!.value,
      localisationCountry: this.editForm.get(['localisationCountry'])!.value,
      localisationTown: this.editForm.get(['localisationTown'])!.value,
      localisationRegion: this.editForm.get(['localisationRegion'])!.value,
      localisationLocality: this.editForm.get(['localisationLocality'])!.value,
      isSite: this.editForm.get(['isSite'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value,
      prestationServiceId: this.editForm.get(['prestationServiceId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>): void {
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
