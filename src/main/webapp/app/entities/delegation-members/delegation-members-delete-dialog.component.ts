import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from './delegation-members.service';

@Component({
  templateUrl: './delegation-members-delete-dialog.component.html'
})
export class DelegationMembersDeleteDialogComponent {
  delegationMembers?: IDelegationMembers;

  constructor(
    protected delegationMembersService: DelegationMembersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.delegationMembersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('delegationMembersListModification');
      this.activeModal.close();
    });
  }
}
