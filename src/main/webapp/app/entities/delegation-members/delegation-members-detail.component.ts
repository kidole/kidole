import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDelegationMembers } from 'app/shared/model/delegation-members.model';

@Component({
  selector: 'jhi-delegation-members-detail',
  templateUrl: './delegation-members-detail.component.html'
})
export class DelegationMembersDetailComponent implements OnInit {
  delegationMembers: IDelegationMembers | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegationMembers }) => {
      this.delegationMembers = delegationMembers;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
