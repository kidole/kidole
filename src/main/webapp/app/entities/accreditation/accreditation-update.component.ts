import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAccreditation, Accreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from './accreditation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-accreditation-update',
  templateUrl: './accreditation-update.component.html'
})
export class AccreditationUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    accreditationName: [null, [Validators.required]],
    firstName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    lastName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    accreditationEmail: [
      null,
      [Validators.required, Validators.minLength(3), Validators.maxLength(1024), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]
    ],
    accreditationStatus: [null, [Validators.required]],
    accreditationDetail: [null, [Validators.required]],
    competitionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accreditationService: AccreditationService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditation }) => {
      this.updateForm(accreditation);

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

  updateForm(accreditation: IAccreditation): void {
    this.editForm.patchValue({
      id: accreditation.id,
      accreditationName: accreditation.accreditationName,
      firstName: accreditation.firstName,
      lastName: accreditation.lastName,
      accreditationEmail: accreditation.accreditationEmail,
      accreditationStatus: accreditation.accreditationStatus,
      accreditationDetail: accreditation.accreditationDetail,
      competitionId: accreditation.competitionId
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
    const accreditation = this.createFromForm();
    if (accreditation.id !== undefined) {
      this.subscribeToSaveResponse(this.accreditationService.update(accreditation));
    } else {
      this.subscribeToSaveResponse(this.accreditationService.create(accreditation));
    }
  }

  private createFromForm(): IAccreditation {
    return {
      ...new Accreditation(),
      id: this.editForm.get(['id'])!.value,
      accreditationName: this.editForm.get(['accreditationName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      accreditationEmail: this.editForm.get(['accreditationEmail'])!.value,
      accreditationStatus: this.editForm.get(['accreditationStatus'])!.value,
      accreditationDetail: this.editForm.get(['accreditationDetail'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccreditation>>): void {
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
