import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAccreditationStep, AccreditationStep } from 'app/shared/model/accreditation-step.model';
import { AccreditationStepService } from './accreditation-step.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-accreditation-step-update',
  templateUrl: './accreditation-step-update.component.html'
})
export class AccreditationStepUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    debut: [],
    fin: [],
    numero: [],
    type: [],
    competitionId: []
  });

  constructor(
    protected accreditationStepService: AccreditationStepService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditationStep }) => {
      this.updateForm(accreditationStep);

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

  updateForm(accreditationStep: IAccreditationStep): void {
    this.editForm.patchValue({
      id: accreditationStep.id,
      debut: accreditationStep.debut != null ? accreditationStep.debut.format(DATE_TIME_FORMAT) : null,
      fin: accreditationStep.fin != null ? accreditationStep.fin.format(DATE_TIME_FORMAT) : null,
      numero: accreditationStep.numero,
      type: accreditationStep.type,
      competitionId: accreditationStep.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accreditationStep = this.createFromForm();
    if (accreditationStep.id !== undefined) {
      this.subscribeToSaveResponse(this.accreditationStepService.update(accreditationStep));
    } else {
      this.subscribeToSaveResponse(this.accreditationStepService.create(accreditationStep));
    }
  }

  private createFromForm(): IAccreditationStep {
    return {
      ...new AccreditationStep(),
      id: this.editForm.get(['id'])!.value,
      debut: this.editForm.get(['debut'])!.value != null ? moment(this.editForm.get(['debut'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value != null ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      numero: this.editForm.get(['numero'])!.value,
      type: this.editForm.get(['type'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccreditationStep>>): void {
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

  trackById(index: number, item: ICompetition): any {
    return item.id;
  }
}
