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

import { IConfrontation, Confrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from 'app/entities/match-sheet/match-sheet.service';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from 'app/entities/localisation/localisation.service';
import { IJournee } from 'app/shared/model/journee.model';
import { JourneeService } from 'app/entities/journee/journee.service';

type SelectableEntity = IMatchSheet | ILocalisation | IJournee;

@Component({
  selector: 'jhi-confrontation-update',
  templateUrl: './confrontation-update.component.html'
})
export class ConfrontationUpdateComponent implements OnInit {
  isSaving = false;

  matchsheets: IMatchSheet[] = [];

  localisations: ILocalisation[] = [];

  journees: IJournee[] = [];

  editForm = this.fb.group({
    id: [],
    confrontationName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    confrontationDetails: [],
    matchsheetId: [],
    localisationId: [],
    journeeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected confrontationService: ConfrontationService,
    protected matchSheetService: MatchSheetService,
    protected localisationService: LocalisationService,
    protected journeeService: JourneeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ confrontation }) => {
      this.updateForm(confrontation);

      this.matchSheetService
        .query({ filter: 'confrontation-is-null' })
        .pipe(
          map((res: HttpResponse<IMatchSheet[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMatchSheet[]) => {
          if (!confrontation.matchsheetId) {
            this.matchsheets = resBody;
          } else {
            this.matchSheetService
              .find(confrontation.matchsheetId)
              .pipe(
                map((subRes: HttpResponse<IMatchSheet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMatchSheet[]) => {
                this.matchsheets = concatRes;
              });
          }
        });

      this.localisationService
        .query({ filter: 'confrontation-is-null' })
        .pipe(
          map((res: HttpResponse<ILocalisation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocalisation[]) => {
          if (!confrontation.localisationId) {
            this.localisations = resBody;
          } else {
            this.localisationService
              .find(confrontation.localisationId)
              .pipe(
                map((subRes: HttpResponse<ILocalisation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocalisation[]) => {
                this.localisations = concatRes;
              });
          }
        });

      this.journeeService
        .query()
        .pipe(
          map((res: HttpResponse<IJournee[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IJournee[]) => (this.journees = resBody));
    });
  }

  updateForm(confrontation: IConfrontation): void {
    this.editForm.patchValue({
      id: confrontation.id,
      confrontationName: confrontation.confrontationName,
      startDate: confrontation.startDate != null ? confrontation.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: confrontation.endDate != null ? confrontation.endDate.format(DATE_TIME_FORMAT) : null,
      confrontationDetails: confrontation.confrontationDetails,
      matchsheetId: confrontation.matchsheetId,
      localisationId: confrontation.localisationId,
      journeeId: confrontation.journeeId
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
    const confrontation = this.createFromForm();
    if (confrontation.id !== undefined) {
      this.subscribeToSaveResponse(this.confrontationService.update(confrontation));
    } else {
      this.subscribeToSaveResponse(this.confrontationService.create(confrontation));
    }
  }

  private createFromForm(): IConfrontation {
    return {
      ...new Confrontation(),
      id: this.editForm.get(['id'])!.value,
      confrontationName: this.editForm.get(['confrontationName'])!.value,
      startDate:
        this.editForm.get(['startDate'])!.value != null ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value != null ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      confrontationDetails: this.editForm.get(['confrontationDetails'])!.value,
      matchsheetId: this.editForm.get(['matchsheetId'])!.value,
      localisationId: this.editForm.get(['localisationId'])!.value,
      journeeId: this.editForm.get(['journeeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfrontation>>): void {
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
