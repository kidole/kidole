import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IRubrique, Rubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-rubrique-update',
  templateUrl: './rubrique-update.component.html'
})
export class RubriqueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rubriqueName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    rubriqueDetails: [null, [Validators.required]],
    rubriqueImage: [],
    rubriqueImageContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected rubriqueService: RubriqueService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rubrique }) => {
      this.updateForm(rubrique);
    });
  }

  updateForm(rubrique: IRubrique): void {
    this.editForm.patchValue({
      id: rubrique.id,
      rubriqueName: rubrique.rubriqueName,
      rubriqueDetails: rubrique.rubriqueDetails,
      rubriqueImage: rubrique.rubriqueImage,
      rubriqueImageContentType: rubrique.rubriqueImageContentType
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('kidoleApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rubrique = this.createFromForm();
    if (rubrique.id !== undefined) {
      this.subscribeToSaveResponse(this.rubriqueService.update(rubrique));
    } else {
      this.subscribeToSaveResponse(this.rubriqueService.create(rubrique));
    }
  }

  private createFromForm(): IRubrique {
    return {
      ...new Rubrique(),
      id: this.editForm.get(['id'])!.value,
      rubriqueName: this.editForm.get(['rubriqueName'])!.value,
      rubriqueDetails: this.editForm.get(['rubriqueDetails'])!.value,
      rubriqueImageContentType: this.editForm.get(['rubriqueImageContentType'])!.value,
      rubriqueImage: this.editForm.get(['rubriqueImage'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRubrique>>): void {
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
