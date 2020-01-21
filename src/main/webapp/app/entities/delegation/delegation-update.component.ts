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

  delegationmembers: IDelegationMembers[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    country: [],
    locality: [],
    code1: [],
    delegationMembersId: []
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
          if (!delegation.delegationMembersId) {
            this.delegationmembers = resBody;
          } else {
            this.delegationMembersService
              .find(delegation.delegationMembersId)
              .pipe(
                map((subRes: HttpResponse<IDelegationMembers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDelegationMembers[]) => {
                this.delegationmembers = concatRes;
              });
          }
        });
    });
  }

  updateForm(delegation: IDelegation): void {
    this.editForm.patchValue({
      id: delegation.id,
      name: delegation.name,
      country: delegation.country,
      locality: delegation.locality,
      code1: delegation.code1,
      delegationMembersId: delegation.delegationMembersId
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
      name: this.editForm.get(['name'])!.value,
      country: this.editForm.get(['country'])!.value,
      locality: this.editForm.get(['locality'])!.value,
      code1: this.editForm.get(['code1'])!.value,
      delegationMembersId: this.editForm.get(['delegationMembersId'])!.value
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
