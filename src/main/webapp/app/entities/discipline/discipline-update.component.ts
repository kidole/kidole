import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDiscipline, Discipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from './discipline.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';
import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from 'app/entities/phase/phase.service';

type SelectableEntity = ICompetition | IPhase;

@Component({
  selector: 'jhi-discipline-update',
  templateUrl: './discipline-update.component.html'
})
export class DisciplineUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  phases: IPhase[] = [];

  editForm = this.fb.group({
    id: [],
    disciplineName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    sexGender: [null, [Validators.required]],
    competitionId: [],
    phaseId: []
  });

  constructor(
    protected disciplineService: DisciplineService,
    protected competitionService: CompetitionService,
    protected phaseService: PhaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discipline }) => {
      this.updateForm(discipline);

      this.competitionService
        .query()
        .pipe(
          map((res: HttpResponse<ICompetition[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICompetition[]) => (this.competitions = resBody));

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

  updateForm(discipline: IDiscipline): void {
    this.editForm.patchValue({
      id: discipline.id,
      disciplineName: discipline.disciplineName,
      sexGender: discipline.sexGender,
      competitionId: discipline.competitionId,
      phaseId: discipline.phaseId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discipline = this.createFromForm();
    if (discipline.id !== undefined) {
      this.subscribeToSaveResponse(this.disciplineService.update(discipline));
    } else {
      this.subscribeToSaveResponse(this.disciplineService.create(discipline));
    }
  }

  private createFromForm(): IDiscipline {
    return {
      ...new Discipline(),
      id: this.editForm.get(['id'])!.value,
      disciplineName: this.editForm.get(['disciplineName'])!.value,
      sexGender: this.editForm.get(['sexGender'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value,
      phaseId: this.editForm.get(['phaseId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscipline>>): void {
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
