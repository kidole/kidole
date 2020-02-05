import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAccreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from 'app/entities/accreditation/accreditation.service';

type SelectableEntity = IUser | IAccreditation;

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  accreditations: IAccreditation[] = [];

  editForm = this.fb.group({
    id: [],
    gender: [null, [Validators.required]],
    photo: [null, [Validators.required]],
    photoContentType: [],
    dateOfBirth: [null, [Validators.required]],
    placeOfBbirth: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    clubOrigin: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    nationality: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    height: [null, [Validators.required, Validators.min(1), Validators.max(4)]],
    weight: [null, [Validators.required, Validators.min(5), Validators.max(500)]],
    manuality: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    nic: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    phone: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(1024)]],
    discipline: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    categorie: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    teamName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    functionOn: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    titleAs: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    residentCity: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    pressID: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    pressAgence: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    bataillonRattachement: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    socialDenomination: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    locationBuilding: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    userId: [],
    accreditationId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected profileService: ProfileService,
    protected userService: UserService,
    protected accreditationService: AccreditationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.accreditationService
        .query({ filter: 'profile-is-null' })
        .pipe(
          map((res: HttpResponse<IAccreditation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAccreditation[]) => {
          if (!profile.accreditationId) {
            this.accreditations = resBody;
          } else {
            this.accreditationService
              .find(profile.accreditationId)
              .pipe(
                map((subRes: HttpResponse<IAccreditation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAccreditation[]) => {
                this.accreditations = concatRes;
              });
          }
        });
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      gender: profile.gender,
      photo: profile.photo,
      photoContentType: profile.photoContentType,
      dateOfBirth: profile.dateOfBirth != null ? profile.dateOfBirth.format(DATE_TIME_FORMAT) : null,
      placeOfBbirth: profile.placeOfBbirth,
      clubOrigin: profile.clubOrigin,
      nationality: profile.nationality,
      height: profile.height,
      weight: profile.weight,
      manuality: profile.manuality,
      nic: profile.nic,
      phone: profile.phone,
      discipline: profile.discipline,
      categorie: profile.categorie,
      teamName: profile.teamName,
      functionOn: profile.functionOn,
      titleAs: profile.titleAs,
      residentCity: profile.residentCity,
      pressID: profile.pressID,
      pressAgence: profile.pressAgence,
      bataillonRattachement: profile.bataillonRattachement,
      socialDenomination: profile.socialDenomination,
      locationBuilding: profile.locationBuilding,
      userId: profile.userId,
      accreditationId: profile.accreditationId
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
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      dateOfBirth:
        this.editForm.get(['dateOfBirth'])!.value != null ? moment(this.editForm.get(['dateOfBirth'])!.value, DATE_TIME_FORMAT) : undefined,
      placeOfBbirth: this.editForm.get(['placeOfBbirth'])!.value,
      clubOrigin: this.editForm.get(['clubOrigin'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      height: this.editForm.get(['height'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      manuality: this.editForm.get(['manuality'])!.value,
      nic: this.editForm.get(['nic'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      discipline: this.editForm.get(['discipline'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      teamName: this.editForm.get(['teamName'])!.value,
      functionOn: this.editForm.get(['functionOn'])!.value,
      titleAs: this.editForm.get(['titleAs'])!.value,
      residentCity: this.editForm.get(['residentCity'])!.value,
      pressID: this.editForm.get(['pressID'])!.value,
      pressAgence: this.editForm.get(['pressAgence'])!.value,
      bataillonRattachement: this.editForm.get(['bataillonRattachement'])!.value,
      socialDenomination: this.editForm.get(['socialDenomination'])!.value,
      locationBuilding: this.editForm.get(['locationBuilding'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      accreditationId: this.editForm.get(['accreditationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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
