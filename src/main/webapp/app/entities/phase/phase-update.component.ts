import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPhase, Phase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from 'app/entities/discipline/discipline.service';

@Component({
  selector: 'jhi-phase-update',
  templateUrl: './phase-update.component.html'
})
export class PhaseUpdateComponent implements OnInit {
  isSaving = false;

  disciplines: IDiscipline[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    numero: [],
    dayNumber: [],
    disciplineId: []
  });

  constructor(
    protected phaseService: PhaseService,
    protected disciplineService: DisciplineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phase }) => {
      this.updateForm(phase);

      this.disciplineService
        .query()
        .pipe(
          map((res: HttpResponse<IDiscipline[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDiscipline[]) => (this.disciplines = resBody));
    });
  }

  updateForm(phase: IPhase): void {
    this.editForm.patchValue({
      id: phase.id,
      name: phase.name,
      numero: phase.numero,
      dayNumber: phase.dayNumber,
      disciplineId: phase.disciplineId
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
      name: this.editForm.get(['name'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      dayNumber: this.editForm.get(['dayNumber'])!.value,
      disciplineId: this.editForm.get(['disciplineId'])!.value
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

  trackById(index: number, item: IDiscipline): any {
    return item.id;
  }
}
