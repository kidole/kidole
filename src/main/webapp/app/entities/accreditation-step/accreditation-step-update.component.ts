import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAccreditationStep, AccreditationStep } from 'app/shared/model/accreditation-step.model';
import { AccreditationStepService } from './accreditation-step.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
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
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    accreditationStepnumber: [null, [Validators.required]],
    accreditationType: [null, [Validators.required]],
    isPublic: [],
    uri: [],
    fields: [],
    competitionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
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
      startTime: accreditationStep.startTime != null ? accreditationStep.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: accreditationStep.endTime != null ? accreditationStep.endTime.format(DATE_TIME_FORMAT) : null,
      accreditationStepnumber: accreditationStep.accreditationStepnumber,
      accreditationType: accreditationStep.accreditationType,
      isPublic: accreditationStep.isPublic,
      uri: accreditationStep.uri,
      fields: accreditationStep.fields,
      competitionId: accreditationStep.competitionId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('kidoleApp.error', { ...err, key: 'error.file.' + err.key })
      );
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
      startTime:
        this.editForm.get(['startTime'])!.value != null ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value != null ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      accreditationStepnumber: this.editForm.get(['accreditationStepnumber'])!.value,
      accreditationType: this.editForm.get(['accreditationType'])!.value,
      isPublic: this.editForm.get(['isPublic'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      fields: this.editForm.get(['fields'])!.value,
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
