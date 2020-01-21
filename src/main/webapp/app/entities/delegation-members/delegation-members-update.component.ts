import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDelegationMembers, DelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from './delegation-members.service';

@Component({
  selector: 'jhi-delegation-members-update',
  templateUrl: './delegation-members-update.component.html'
})
export class DelegationMembersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    state: [],
    code1: [],
    detail: []
  });

  constructor(
    protected delegationMembersService: DelegationMembersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegationMembers }) => {
      this.updateForm(delegationMembers);
    });
  }

  updateForm(delegationMembers: IDelegationMembers): void {
    this.editForm.patchValue({
      id: delegationMembers.id,
      state: delegationMembers.state,
      code1: delegationMembers.code1,
      detail: delegationMembers.detail
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
      state: this.editForm.get(['state'])!.value,
      code1: this.editForm.get(['code1'])!.value,
      detail: this.editForm.get(['detail'])!.value
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
}
