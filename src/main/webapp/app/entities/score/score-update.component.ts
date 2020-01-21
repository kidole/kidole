import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IScore, Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team/team.service';
import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';

type SelectableEntity = ITeam | IConfrontation;

@Component({
  selector: 'jhi-score-update',
  templateUrl: './score-update.component.html'
})
export class ScoreUpdateComponent implements OnInit {
  isSaving = false;

  teams: ITeam[] = [];

  confrontations: IConfrontation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    scoreIndex: [],
    value: [],
    unite: [],
    teamId: [],
    confrontationId: []
  });

  constructor(
    protected scoreService: ScoreService,
    protected teamService: TeamService,
    protected confrontationService: ConfrontationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ score }) => {
      this.updateForm(score);

      this.teamService
        .query({ filter: 'score-is-null' })
        .pipe(
          map((res: HttpResponse<ITeam[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITeam[]) => {
          if (!score.teamId) {
            this.teams = resBody;
          } else {
            this.teamService
              .find(score.teamId)
              .pipe(
                map((subRes: HttpResponse<ITeam>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeam[]) => {
                this.teams = concatRes;
              });
          }
        });

      this.confrontationService
        .query()
        .pipe(
          map((res: HttpResponse<IConfrontation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConfrontation[]) => (this.confrontations = resBody));
    });
  }

  updateForm(score: IScore): void {
    this.editForm.patchValue({
      id: score.id,
      name: score.name,
      scoreIndex: score.scoreIndex,
      value: score.value,
      unite: score.unite,
      teamId: score.teamId,
      confrontationId: score.confrontationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const score = this.createFromForm();
    if (score.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreService.update(score));
    } else {
      this.subscribeToSaveResponse(this.scoreService.create(score));
    }
  }

  private createFromForm(): IScore {
    return {
      ...new Score(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      scoreIndex: this.editForm.get(['scoreIndex'])!.value,
      value: this.editForm.get(['value'])!.value,
      unite: this.editForm.get(['unite'])!.value,
      teamId: this.editForm.get(['teamId'])!.value,
      confrontationId: this.editForm.get(['confrontationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>): void {
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
