import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDelegation } from 'app/shared/model/delegation.model';
import { DelegationService } from './delegation.service';
import { DelegationDeleteDialogComponent } from './delegation-delete-dialog.component';

@Component({
  selector: 'jhi-delegation',
  templateUrl: './delegation.component.html'
})
export class DelegationComponent implements OnInit, OnDestroy {
  delegations?: IDelegation[];
  eventSubscriber?: Subscription;

  constructor(protected delegationService: DelegationService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.delegationService.query().subscribe((res: HttpResponse<IDelegation[]>) => {
      this.delegations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDelegations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDelegation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDelegations(): void {
    this.eventSubscriber = this.eventManager.subscribe('delegationListModification', () => this.loadAll());
  }

  delete(delegation: IDelegation): void {
    const modalRef = this.modalService.open(DelegationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.delegation = delegation;
  }
}
