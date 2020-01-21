import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFiles, Files } from 'app/shared/model/files.model';
import { FilesService } from './files.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from 'app/entities/competition-services-offer/competition-services-offer.service';
import { IPrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

type SelectableEntity = ICompetitionServicesOffer | IPrestationService | IRubrique | ICompetition;

@Component({
  selector: 'jhi-files-update',
  templateUrl: './files-update.component.html'
})
export class FilesUpdateComponent implements OnInit {
  isSaving = false;

  competitionservicesoffers: ICompetitionServicesOffer[] = [];

  prestationservices: IPrestationService[] = [];

  rubriques: IRubrique[] = [];

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    publique: [],
    content: [],
    contentContentType: [],
    competitionServicesOfferId: [],
    prestationServiceId: [],
    rubriqueId: [],
    competitionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected filesService: FilesService,
    protected competitionServicesOfferService: CompetitionServicesOfferService,
    protected prestationServiceService: PrestationServiceService,
    protected rubriqueService: RubriqueService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ files }) => {
      this.updateForm(files);

      this.competitionServicesOfferService
        .query()
        .pipe(
          map((res: HttpResponse<ICompetitionServicesOffer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICompetitionServicesOffer[]) => (this.competitionservicesoffers = resBody));

      this.prestationServiceService
        .query()
        .pipe(
          map((res: HttpResponse<IPrestationService[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPrestationService[]) => (this.prestationservices = resBody));

      this.rubriqueService
        .query()
        .pipe(
          map((res: HttpResponse<IRubrique[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IRubrique[]) => (this.rubriques = resBody));

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

  updateForm(files: IFiles): void {
    this.editForm.patchValue({
      id: files.id,
      name: files.name,
      type: files.type,
      publique: files.publique,
      content: files.content,
      contentContentType: files.contentContentType,
      competitionServicesOfferId: files.competitionServicesOfferId,
      prestationServiceId: files.prestationServiceId,
      rubriqueId: files.rubriqueId,
      competitionId: files.competitionId
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
    const files = this.createFromForm();
    if (files.id !== undefined) {
      this.subscribeToSaveResponse(this.filesService.update(files));
    } else {
      this.subscribeToSaveResponse(this.filesService.create(files));
    }
  }

  private createFromForm(): IFiles {
    return {
      ...new Files(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      publique: this.editForm.get(['publique'])!.value,
      contentContentType: this.editForm.get(['contentContentType'])!.value,
      content: this.editForm.get(['content'])!.value,
      competitionServicesOfferId: this.editForm.get(['competitionServicesOfferId'])!.value,
      prestationServiceId: this.editForm.get(['prestationServiceId'])!.value,
      rubriqueId: this.editForm.get(['rubriqueId'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFiles>>): void {
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
