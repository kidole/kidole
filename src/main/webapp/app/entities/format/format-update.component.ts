import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IFormat, Format } from 'app/shared/model/format.model';
import { FormatService } from './format.service';
import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from 'app/entities/phase/phase.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

type SelectableEntity = IPhase | ICompetition;

@Component({
  selector: 'jhi-format-update',
  templateUrl: './format-update.component.html'
})
export class FormatUpdateComponent implements OnInit {
  isSaving = false;

  phases: IPhase[] = [];

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    formatName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    winerQty: [null, [Validators.min(0)]],
    phaseId: [],
    competitionId: []
  });

  constructor(
    protected formatService: FormatService,
    protected phaseService: PhaseService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ format }) => {
      this.updateForm(format);

      this.phaseService
        .query({ filter: 'format-is-null' })
        .pipe(
          map((res: HttpResponse<IPhase[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPhase[]) => {
          if (!format.phaseId) {
            this.phases = resBody;
          } else {
            this.phaseService
              .find(format.phaseId)
              .pipe(
                map((subRes: HttpResponse<IPhase>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPhase[]) => {
                this.phases = concatRes;
              });
          }
        });

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

  updateForm(format: IFormat): void {
    this.editForm.patchValue({
      id: format.id,
      formatName: format.formatName,
      winerQty: format.winerQty,
      phaseId: format.phaseId,
      competitionId: format.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const format = this.createFromForm();
    if (format.id !== undefined) {
      this.subscribeToSaveResponse(this.formatService.update(format));
    } else {
      this.subscribeToSaveResponse(this.formatService.create(format));
    }
  }

  private createFromForm(): IFormat {
    return {
      ...new Format(),
      id: this.editForm.get(['id'])!.value,
      formatName: this.editForm.get(['formatName'])!.value,
      winerQty: this.editForm.get(['winerQty'])!.value,
      phaseId: this.editForm.get(['phaseId'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormat>>): void {
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
