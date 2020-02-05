import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompetitionServicesOffer, CompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { CompetitionServicesOfferService } from './competition-services-offer.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique/rubrique.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

type SelectableEntity = IRubrique | ICompetition;

@Component({
  selector: 'jhi-competition-services-offer-update',
  templateUrl: './competition-services-offer-update.component.html'
})
export class CompetitionServicesOfferUpdateComponent implements OnInit {
  isSaving = false;

  rubrics: IRubrique[] = [];

  competitions: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    competitionServicesOfferName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    competitionServicesOfferDetail: [null, [Validators.required]],
    competitionServicesOfferUrl: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    rubricId: [],
    competitionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected competitionServicesOfferService: CompetitionServicesOfferService,
    protected rubriqueService: RubriqueService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionServicesOffer }) => {
      this.updateForm(competitionServicesOffer);

      this.rubriqueService
        .query({ filter: 'competitionservicesoffer-is-null' })
        .pipe(
          map((res: HttpResponse<IRubrique[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IRubrique[]) => {
          if (!competitionServicesOffer.rubricId) {
            this.rubrics = resBody;
          } else {
            this.rubriqueService
              .find(competitionServicesOffer.rubricId)
              .pipe(
                map((subRes: HttpResponse<IRubrique>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRubrique[]) => {
                this.rubrics = concatRes;
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

  updateForm(competitionServicesOffer: ICompetitionServicesOffer): void {
    this.editForm.patchValue({
      id: competitionServicesOffer.id,
      competitionServicesOfferName: competitionServicesOffer.competitionServicesOfferName,
      competitionServicesOfferDetail: competitionServicesOffer.competitionServicesOfferDetail,
      competitionServicesOfferUrl: competitionServicesOffer.competitionServicesOfferUrl,
      rubricId: competitionServicesOffer.rubricId,
      competitionId: competitionServicesOffer.competitionId
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
    const competitionServicesOffer = this.createFromForm();
    if (competitionServicesOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionServicesOfferService.update(competitionServicesOffer));
    } else {
      this.subscribeToSaveResponse(this.competitionServicesOfferService.create(competitionServicesOffer));
    }
  }

  private createFromForm(): ICompetitionServicesOffer {
    return {
      ...new CompetitionServicesOffer(),
      id: this.editForm.get(['id'])!.value,
      competitionServicesOfferName: this.editForm.get(['competitionServicesOfferName'])!.value,
      competitionServicesOfferDetail: this.editForm.get(['competitionServicesOfferDetail'])!.value,
      competitionServicesOfferUrl: this.editForm.get(['competitionServicesOfferUrl'])!.value,
      rubricId: this.editForm.get(['rubricId'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitionServicesOffer>>): void {
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
