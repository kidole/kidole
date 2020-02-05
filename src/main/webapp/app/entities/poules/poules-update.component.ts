import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPoules, Poules } from 'app/shared/model/poules.model';
import { PoulesService } from './poules.service';

@Component({
  selector: 'jhi-poules-update',
  templateUrl: './poules-update.component.html'
})
export class PoulesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    poulesName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]]
  });

  constructor(protected poulesService: PoulesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poules }) => {
      this.updateForm(poules);
    });
  }

  updateForm(poules: IPoules): void {
    this.editForm.patchValue({
      id: poules.id,
      poulesName: poules.poulesName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poules = this.createFromForm();
    if (poules.id !== undefined) {
      this.subscribeToSaveResponse(this.poulesService.update(poules));
    } else {
      this.subscribeToSaveResponse(this.poulesService.create(poules));
    }
  }

  private createFromForm(): IPoules {
    return {
      ...new Poules(),
      id: this.editForm.get(['id'])!.value,
      poulesName: this.editForm.get(['poulesName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoules>>): void {
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
