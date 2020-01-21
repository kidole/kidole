import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDelegationMembers } from 'app/shared/model/delegation-members.model';
import { DelegationMembersService } from './delegation-members.service';
import { DelegationMembersDeleteDialogComponent } from './delegation-members-delete-dialog.component';

@Component({
  selector: 'jhi-delegation-members',
  templateUrl: './delegation-members.component.html'
})
export class DelegationMembersComponent implements OnInit, OnDestroy {
  delegationMembers?: IDelegationMembers[];
  eventSubscriber?: Subscription;

  constructor(
    protected delegationMembersService: DelegationMembersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.delegationMembersService.query().subscribe((res: HttpResponse<IDelegationMembers[]>) => {
      this.delegationMembers = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDelegationMembers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDelegationMembers): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDelegationMembers(): void {
    this.eventSubscriber = this.eventManager.subscribe('delegationMembersListModification', () => this.loadAll());
  }

  delete(delegationMembers: IDelegationMembers): void {
    const modalRef = this.modalService.open(DelegationMembersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.delegationMembers = delegationMembers;
  }
}
