import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhase, Phase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';

@Component({
  selector: 'jhi-phase-update',
  templateUrl: './phase-update.component.html'
})
export class PhaseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    phaseName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    phaseNumber: [null, [Validators.min(1)]],
    phaseDayNumber: [null, [Validators.min(1)]]
  });

  constructor(protected phaseService: PhaseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phase }) => {
      this.updateForm(phase);
    });
  }

  updateForm(phase: IPhase): void {
    this.editForm.patchValue({
      id: phase.id,
      phaseName: phase.phaseName,
      phaseNumber: phase.phaseNumber,
      phaseDayNumber: phase.phaseDayNumber
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phase = this.createFromForm();
    if (phase.id !== undefined) {
      this.subscribeToSaveResponse(this.phaseService.update(phase));
    } else {
      this.subscribeToSaveResponse(this.phaseService.create(phase));
    }
  }

  private createFromForm(): IPhase {
    return {
      ...new Phase(),
      id: this.editForm.get(['id'])!.value,
      phaseName: this.editForm.get(['phaseName'])!.value,
      phaseNumber: this.editForm.get(['phaseNumber'])!.value,
      phaseDayNumber: this.editForm.get(['phaseDayNumber'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhase>>): void {
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
