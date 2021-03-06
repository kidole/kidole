import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IOptions, Options } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-options-update',
  templateUrl: './options-update.component.html'
})
export class OptionsUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    optionsName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    optionsValue1: [null, [Validators.required]],
    optionsValue2: [null, [Validators.required, Validators.min(0)]],
    competitionId: []
  });

  constructor(
    protected optionsService: OptionsService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ options }) => {
      this.updateForm(options);

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

  updateForm(options: IOptions): void {
    this.editForm.patchValue({
      id: options.id,
      optionsName: options.optionsName,
      optionsValue1: options.optionsValue1,
      optionsValue2: options.optionsValue2,
      competitionId: options.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const options = this.createFromForm();
    if (options.id !== undefined) {
      this.subscribeToSaveResponse(this.optionsService.update(options));
    } else {
      this.subscribeToSaveResponse(this.optionsService.create(options));
    }
  }

  private createFromForm(): IOptions {
    return {
      ...new Options(),
      id: this.editForm.get(['id'])!.value,
      optionsName: this.editForm.get(['optionsName'])!.value,
      optionsValue1: this.editForm.get(['optionsValue1'])!.value,
      optionsValue2: this.editForm.get(['optionsValue2'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptions>>): void {
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
