import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditation } from 'app/shared/model/accreditation.model';
import { AccreditationService } from './accreditation.service';
import { AccreditationDeleteDialogComponent } from './accreditation-delete-dialog.component';

@Component({
  selector: 'jhi-accreditation',
  templateUrl: './accreditation.component.html'
})
export class AccreditationComponent implements OnInit, OnDestroy {
  accreditations?: IAccreditation[];
  eventSubscriber?: Subscription;

  constructor(
    protected accreditationService: AccreditationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.accreditationService.query().subscribe((res: HttpResponse<IAccreditation[]>) => {
      this.accreditations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccreditations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccreditation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccreditations(): void {
    this.eventSubscriber = this.eventManager.subscribe('accreditationListModification', () => this.loadAll());
  }

  delete(accreditation: IAccreditation): void {
    const modalRef = this.modalService.open(AccreditationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accreditation = accreditation;
  }
}
