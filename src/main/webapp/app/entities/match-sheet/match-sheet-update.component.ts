import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMatchSheet, MatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from './match-sheet.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-match-sheet-update',
  templateUrl: './match-sheet-update.component.html'
})
export class MatchSheetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    matchSheetName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    matchSheetResume: [null, [Validators.required]],
    isfirst: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected matchSheetService: MatchSheetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchSheet }) => {
      this.updateForm(matchSheet);
    });
  }

  updateForm(matchSheet: IMatchSheet): void {
    this.editForm.patchValue({
      id: matchSheet.id,
      matchSheetName: matchSheet.matchSheetName,
      matchSheetResume: matchSheet.matchSheetResume,
      isfirst: matchSheet.isfirst
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
    const matchSheet = this.createFromForm();
    if (matchSheet.id !== undefined) {
      this.subscribeToSaveResponse(this.matchSheetService.update(matchSheet));
    } else {
      this.subscribeToSaveResponse(this.matchSheetService.create(matchSheet));
    }
  }

  private createFromForm(): IMatchSheet {
    return {
      ...new MatchSheet(),
      id: this.editForm.get(['id'])!.value,
      matchSheetName: this.editForm.get(['matchSheetName'])!.value,
      matchSheetResume: this.editForm.get(['matchSheetResume'])!.value,
      isfirst: this.editForm.get(['isfirst'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatchSheet>>): void {
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
