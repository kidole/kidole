import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelegationMembers } from 'app/shared/model/delegation-members.model';

@Component({
  selector: 'jhi-delegation-members-detail',
  templateUrl: './delegation-members-detail.component.html'
})
export class DelegationMembersDetailComponent implements OnInit {
  delegationMembers: IDelegationMembers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegationMembers }) => {
      this.delegationMembers = delegationMembers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
