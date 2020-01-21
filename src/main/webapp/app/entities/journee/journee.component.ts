import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJournee } from 'app/shared/model/journee.model';
import { JourneeService } from './journee.service';
import { JourneeDeleteDialogComponent } from './journee-delete-dialog.component';

@Component({
  selector: 'jhi-journee',
  templateUrl: './journee.component.html'
})
export class JourneeComponent implements OnInit, OnDestroy {
  journees?: IJournee[];
  eventSubscriber?: Subscription;

  constructor(protected journeeService: JourneeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.journeeService.query().subscribe((res: HttpResponse<IJournee[]>) => {
      this.journees = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJournees();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJournee): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJournees(): void {
    this.eventSubscriber = this.eventManager.subscribe('journeeListModification', () => this.loadAll());
  }

  delete(journee: IJournee): void {
    const modalRef = this.modalService.open(JourneeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.journee = journee;
  }
}
