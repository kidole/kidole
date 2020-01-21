import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAccreditation, Accreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from './accreditation.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-accreditation-update',
  templateUrl: './accreditation-update.component.html'
})
export class AccreditationUpdateComponent implements OnInit {
  isSaving = false;

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    status: [],
    details: [],
    competitionId: []
  });

  constructor(
    protected accreditationService: AccreditationService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditation }) => {
      this.updateForm(accreditation);

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

  updateForm(accreditation: IAccreditation): void {
    this.editForm.patchValue({
      id: accreditation.id,
      name: accreditation.name,
      status: accreditation.status,
      details: accreditation.details,
      competitionId: accreditation.competitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accreditation = this.createFromForm();
    if (accreditation.id !== undefined) {
      this.subscribeToSaveResponse(this.accreditationService.update(accreditation));
    } else {
      this.subscribeToSaveResponse(this.accreditationService.create(accreditation));
    }
  }

  private createFromForm(): IAccreditation {
    return {
      ...new Accreditation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      status: this.editForm.get(['status'])!.value,
      details: this.editForm.get(['details'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccreditation>>): void {
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
