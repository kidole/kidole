import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IJournee, Journee } from 'app/shared/model/journee.model';
import { JourneeService } from './journee.service';
import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from 'app/entities/phase/phase.service';

@Component({
  selector: 'jhi-journee-update',
  templateUrl: './journee-update.component.html'
})
export class JourneeUpdateComponent implements OnInit {
  isSaving = false;

  phases: IPhase[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    phaseId: []
  });

  constructor(
    protected journeeService: JourneeService,
    protected phaseService: PhaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journee }) => {
      this.updateForm(journee);

      this.phaseService
        .query()
        .pipe(
          map((res: HttpResponse<IPhase[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPhase[]) => (this.phases = resBody));
    });
  }

  updateForm(journee: IJournee): void {
    this.editForm.patchValue({
      id: journee.id,
      name: journee.name,
      phaseId: journee.phaseId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journee = this.createFromForm();
    if (journee.id !== undefined) {
      this.subscribeToSaveResponse(this.journeeService.update(journee));
    } else {
      this.subscribeToSaveResponse(this.journeeService.create(journee));
    }
  }

  private createFromForm(): IJournee {
    return {
      ...new Journee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phaseId: this.editForm.get(['phaseId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJournee>>): void {
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

  trackById(index: number, item: IPhase): any {
    return item.id;
  }
}
