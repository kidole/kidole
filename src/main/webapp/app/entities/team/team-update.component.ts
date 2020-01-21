import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITeam, Team } from 'app/shared/model/team.model';
import { TeamService } from './team.service';
import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';
import { IPoules } from 'app/shared/model/poules.model';
import { PoulesService } from 'app/entities/poules/poules.service';
import { IDelegation } from 'app/shared/model/delegation.model';
import { DelegationService } from 'app/entities/delegation/delegation.service';

type SelectableEntity = IConfrontation | IPoules | IDelegation;

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html'
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;

  confrontations: IConfrontation[] = [];

  poules: IPoules[] = [];

  delegations: IDelegation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    confrontationId: [],
    poulesId: [],
    delegationId: []
  });

  constructor(
    protected teamService: TeamService,
    protected confrontationService: ConfrontationService,
    protected poulesService: PoulesService,
    protected delegationService: DelegationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.updateForm(team);

      this.confrontationService
        .query()
        .pipe(
          map((res: HttpResponse<IConfrontation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConfrontation[]) => (this.confrontations = resBody));

      this.poulesService
        .query()
        .pipe(
          map((res: HttpResponse<IPoules[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPoules[]) => (this.poules = resBody));

      this.delegationService
        .query()
        .pipe(
          map((res: HttpResponse<IDelegation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDelegation[]) => (this.delegations = resBody));
    });
  }

  updateForm(team: ITeam): void {
    this.editForm.patchValue({
      id: team.id,
      name: team.name,
      confrontationId: team.confrontationId,
      poulesId: team.poulesId,
      delegationId: team.delegationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.createFromForm();
    if (team.id !== undefined) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  private createFromForm(): ITeam {
    return {
      ...new Team(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      confrontationId: this.editForm.get(['confrontationId'])!.value,
      poulesId: this.editForm.get(['poulesId'])!.value,
      delegationId: this.editForm.get(['delegationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
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
