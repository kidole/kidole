import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDelegationMembers, DelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from './delegation-members.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-delegation-members-update',
  templateUrl: './delegation-members-update.component.html'
})
export class DelegationMembersUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    delegationMembersState: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegationMembersCode: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegationMembersDetail: [null, [Validators.required]],
    userId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected delegationMembersService: DelegationMembersService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegationMembers }) => {
      this.updateForm(delegationMembers);

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

  updateForm(delegationMembers: IDelegationMembers): void {
    this.editForm.patchValue({
      id: delegationMembers.id,
      delegationMembersState: delegationMembers.delegationMembersState,
      delegationMembersCode: delegationMembers.delegationMembersCode,
      delegationMembersDetail: delegationMembers.delegationMembersDetail,
      userId: delegationMembers.userId
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
    const delegationMembers = this.createFromForm();
    if (delegationMembers.id !== undefined) {
      this.subscribeToSaveResponse(this.delegationMembersService.update(delegationMembers));
    } else {
      this.subscribeToSaveResponse(this.delegationMembersService.create(delegationMembers));
    }
  }

  private createFromForm(): IDelegationMembers {
    return {
      ...new DelegationMembers(),
      id: this.editForm.get(['id'])!.value,
      delegationMembersState: this.editForm.get(['delegationMembersState'])!.value,
      delegationMembersCode: this.editForm.get(['delegationMembersCode'])!.value,
      delegationMembersDetail: this.editForm.get(['delegationMembersDetail'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelegationMembers>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
