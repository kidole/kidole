import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompetition, Competition } from 'app/shared/model/competition.model';
import { CompetitionService } from './competition.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html'
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    debut: [],
    fin: [],
    dateLimit: [],
    detail: [],
    role: [],
    roleContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);
    });
  }

  updateForm(competition: ICompetition): void {
    this.editForm.patchValue({
      id: competition.id,
      name: competition.name,
      debut: competition.debut != null ? competition.debut.format(DATE_TIME_FORMAT) : null,
      fin: competition.fin != null ? competition.fin.format(DATE_TIME_FORMAT) : null,
      dateLimit: competition.dateLimit != null ? competition.dateLimit.format(DATE_TIME_FORMAT) : null,
      detail: competition.detail,
      role: competition.role,
      roleContentType: competition.roleContentType
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
    const competition = this.createFromForm();
    if (competition.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionService.update(competition));
    } else {
      this.subscribeToSaveResponse(this.competitionService.create(competition));
    }
  }

  private createFromForm(): ICompetition {
    return {
      ...new Competition(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      debut: this.editForm.get(['debut'])!.value != null ? moment(this.editForm.get(['debut'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value != null ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      dateLimit:
        this.editForm.get(['dateLimit'])!.value != null ? moment(this.editForm.get(['dateLimit'])!.value, DATE_TIME_FORMAT) : undefined,
      detail: this.editForm.get(['detail'])!.value,
      roleContentType: this.editForm.get(['roleContentType'])!.value,
      role: this.editForm.get(['role'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetition>>): void {
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
}
