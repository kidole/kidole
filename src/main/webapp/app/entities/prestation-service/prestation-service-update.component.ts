import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPrestationService, PrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from './prestation-service.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IRubrique | IUser;

@Component({
  selector: 'jhi-prestation-service-update',
  templateUrl: './prestation-service-update.component.html'
})
export class PrestationServiceUpdateComponent implements OnInit {
  isSaving = false;

  rubriques: IRubrique[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    prestationServiceName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    prestationServiceNameState: [null, [Validators.required]],
    prestationServiceNameDetail: [null, [Validators.required]],
    prestationServiceNameImage: [],
    prestationServiceNameImageContentType: [],
    rubriqueId: [],
    userId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected prestationServiceService: PrestationServiceService,
    protected rubriqueService: RubriqueService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prestationService }) => {
      this.updateForm(prestationService);

      this.rubriqueService
        .query({ filter: 'prestationservice-is-null' })
        .pipe(
          map((res: HttpResponse<IRubrique[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IRubrique[]) => {
          if (!prestationService.rubriqueId) {
            this.rubriques = resBody;
          } else {
            this.rubriqueService
              .find(prestationService.rubriqueId)
              .pipe(
                map((subRes: HttpResponse<IRubrique>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRubrique[]) => {
                this.rubriques = concatRes;
              });
          }
        });

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(prestationService: IPrestationService): void {
    this.editForm.patchValue({
      id: prestationService.id,
      prestationServiceName: prestationService.prestationServiceName,
      prestationServiceNameState: prestationService.prestationServiceNameState,
      prestationServiceNameDetail: prestationService.prestationServiceNameDetail,
      prestationServiceNameImage: prestationService.prestationServiceNameImage,
      prestationServiceNameImageContentType: prestationService.prestationServiceNameImageContentType,
      rubriqueId: prestationService.rubriqueId,
      userId: prestationService.userId
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
    const prestationService = this.createFromForm();
    if (prestationService.id !== undefined) {
      this.subscribeToSaveResponse(this.prestationServiceService.update(prestationService));
    } else {
      this.subscribeToSaveResponse(this.prestationServiceService.create(prestationService));
    }
  }

  private createFromForm(): IPrestationService {
    return {
      ...new PrestationService(),
      id: this.editForm.get(['id'])!.value,
      prestationServiceName: this.editForm.get(['prestationServiceName'])!.value,
      prestationServiceNameState: this.editForm.get(['prestationServiceNameState'])!.value,
      prestationServiceNameDetail: this.editForm.get(['prestationServiceNameDetail'])!.value,
      prestationServiceNameImageContentType: this.editForm.get(['prestationServiceNameImageContentType'])!.value,
      prestationServiceNameImage: this.editForm.get(['prestationServiceNameImage'])!.value,
      rubriqueId: this.editForm.get(['rubriqueId'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrestationService>>): void {
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
