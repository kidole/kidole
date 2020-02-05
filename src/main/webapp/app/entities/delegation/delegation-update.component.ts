import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDelegation, Delegation } from 'app/shared/model/delegation.model';
import { DelegationService } from './delegation.service';
import { IDelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from 'app/entities/delegation-members/delegation-members.service';

@Component({
  selector: 'jhi-delegation-update',
  templateUrl: './delegation-update.component.html'
})
export class DelegationUpdateComponent implements OnInit {
  isSaving = false;

  delegatemembers: IDelegationMembers[] = [];

  editForm = this.fb.group({
    id: [],
    delegationName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegationCountry: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegationLocality: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegationCode: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(1024)]],
    delegateMemberId: []
  });

  constructor(
    protected delegationService: DelegationService,
    protected delegationMembersService: DelegationMembersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegation }) => {
      this.updateForm(delegation);

      this.delegationMembersService
        .query({ filter: 'delegation-is-null' })
        .pipe(
          map((res: HttpResponse<IDelegationMembers[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDelegationMembers[]) => {
          if (!delegation.delegateMemberId) {
            this.delegatemembers = resBody;
          } else {
            this.delegationMembersService
              .find(delegation.delegateMemberId)
              .pipe(
                map((subRes: HttpResponse<IDelegationMembers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDelegationMembers[]) => {
                this.delegatemembers = concatRes;
              });
          }
        });
    });
  }

  updateForm(delegation: IDelegation): void {
    this.editForm.patchValue({
      id: delegation.id,
      delegationName: delegation.delegationName,
      delegationCountry: delegation.delegationCountry,
      delegationLocality: delegation.delegationLocality,
      delegationCode: delegation.delegationCode,
      delegateMemberId: delegation.delegateMemberId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const delegation = this.createFromForm();
    if (delegation.id !== undefined) {
      this.subscribeToSaveResponse(this.delegationService.update(delegation));
    } else {
      this.subscribeToSaveResponse(this.delegationService.create(delegation));
    }
  }

  private createFromForm(): IDelegation {
    return {
      ...new Delegation(),
      id: this.editForm.get(['id'])!.value,
      delegationName: this.editForm.get(['delegationName'])!.value,
      delegationCountry: this.editForm.get(['delegationCountry'])!.value,
      delegationLocality: this.editForm.get(['delegationLocality'])!.value,
      delegationCode: this.editForm.get(['delegationCode'])!.value,
      delegateMemberId: this.editForm.get(['delegateMemberId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelegation>>): void {
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

  trackById(index: number, item: IDelegationMembers): any {
    return item.id;
  }
}
