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

@Component({
  selector: 'jhi-discipline-update',
  templateUrl: './discipline-update.component.html'
})
export class DisciplineUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    sexGender: [],
    competitionId: []
  });

  constructor(
    protected disciplineService: DisciplineService,
    protected competitionService: CompetitionService,
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
    });
  }

  updateForm(discipline: IDiscipline): void {
    this.editForm.patchValue({
      id: discipline.id,
      name: discipline.name,
      sexGender: discipline.sexGender,
      competitionId: discipline.competitionId
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
      name: this.editForm.get(['name'])!.value,
      sexGender: this.editForm.get(['sexGender'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
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

  trackById(index: number, item: ICompetition): any {
    return item.id;
  }
}
