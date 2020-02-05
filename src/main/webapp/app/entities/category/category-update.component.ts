import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from 'app/entities/discipline/discipline.service';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;

  disciplines: IDiscipline[] = [];

  editForm = this.fb.group({
    id: [],
    categoryName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    yearlimit: [null, [Validators.required, Validators.min(0)]],
    teamLimitNumb: [null, [Validators.required, Validators.min(0)]],
    participantLimitByteam: [null, [Validators.required, Validators.min(0)]],
    categoryRule: [null, [Validators.required]],
    disciplineId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categoryService: CategoryService,
    protected disciplineService: DisciplineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);

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

  updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      categoryName: category.categoryName,
      yearlimit: category.yearlimit,
      teamLimitNumb: category.teamLimitNumb,
      participantLimitByteam: category.participantLimitByteam,
      categoryRule: category.categoryRule,
      disciplineId: category.disciplineId
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      categoryName: this.editForm.get(['categoryName'])!.value,
      yearlimit: this.editForm.get(['yearlimit'])!.value,
      teamLimitNumb: this.editForm.get(['teamLimitNumb'])!.value,
      participantLimitByteam: this.editForm.get(['participantLimitByteam'])!.value,
      categoryRule: this.editForm.get(['categoryRule'])!.value,
      disciplineId: this.editForm.get(['disciplineId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
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
