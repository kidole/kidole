import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatchSheet, MatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from './match-sheet.service';

@Component({
  selector: 'jhi-match-sheet-update',
  templateUrl: './match-sheet-update.component.html'
})
export class MatchSheetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    resume: [],
    isfirst: []
  });

  constructor(protected matchSheetService: MatchSheetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchSheet }) => {
      this.updateForm(matchSheet);
    });
  }

  updateForm(matchSheet: IMatchSheet): void {
    this.editForm.patchValue({
      id: matchSheet.id,
      name: matchSheet.name,
      resume: matchSheet.resume,
      isfirst: matchSheet.isfirst
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
      name: this.editForm.get(['name'])!.value,
      resume: this.editForm.get(['resume'])!.value,
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
