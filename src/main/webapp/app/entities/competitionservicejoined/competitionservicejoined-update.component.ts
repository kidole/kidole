import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICompetitionservicejoined, Competitionservicejoined } from 'app/shared/model/competitionservicejoined.model';
import { CompetitionservicejoinedService } from './competitionservicejoined.service';
import { IPrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

type SelectableEntity = IPrestationService | ICompetition;

@Component({
  selector: 'jhi-competitionservicejoined-update',
  templateUrl: './competitionservicejoined-update.component.html'
})
export class CompetitionservicejoinedUpdateComponent implements OnInit {
  isSaving = false;

  prestationservices: IPrestationService[] = [];

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    state: [],
    details: [],
    prestationServiceId: [],
    competitionId: []
  });

  constructor(
    protected competitionservicejoinedService: CompetitionservicejoinedService,
    protected prestationServiceService: PrestationServiceService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionservicejoined }) => {
      this.updateForm(competitionservicejoined);

      this.prestationServiceService
        .query({ filter: 'competitionservicejoined-is-null' })
        .pipe(
          map((res: HttpResponse<IPrestationService[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPrestationService[]) => {
          if (!competitionservicejoined.prestationServiceId) {
            this.prestationservices = resBody;
          } else {
            this.prestationServiceService
              .find(competitionservicejoined.prestationServiceId)
              .pipe(
                map((subRes: HttpResponse<IPrestationService>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPrestationService[]) => {
                this.prestationservices = concatRes;
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

  updateForm(competitionservicejoined: ICompetitionservicejoined): void {
    this.editForm.patchValue({
      id: competitionservicejoined.id,
      state: competitionservicejoined.state,
      details: competitionservicejoined.details,
      prestationServiceId: competitionservicejoined.prestationServiceId,
      competitionId: competitionservicejoined.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competitionservicejoined = this.createFromForm();
    if (competitionservicejoined.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionservicejoinedService.update(competitionservicejoined));
    } else {
      this.subscribeToSaveResponse(this.competitionservicejoinedService.create(competitionservicejoined));
    }
  }

  private createFromForm(): ICompetitionservicejoined {
    return {
      ...new Competitionservicejoined(),
      id: this.editForm.get(['id'])!.value,
      state: this.editForm.get(['state'])!.value,
      details: this.editForm.get(['details'])!.value,
      prestationServiceId: this.editForm.get(['prestationServiceId'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitionservicejoined>>): void {
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
