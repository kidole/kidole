import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPoules } from 'app/shared/model/poules.model';
import { PoulesService } from './poules.service';
import { PoulesDeleteDialogComponent } from './poules-delete-dialog.component';

@Component({
  selector: 'jhi-poules',
  templateUrl: './poules.component.html'
})
export class PoulesComponent implements OnInit, OnDestroy {
  poules?: IPoules[];
  eventSubscriber?: Subscription;

  constructor(protected poulesService: PoulesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.poulesService.query().subscribe((res: HttpResponse<IPoules[]>) => {
      this.poules = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPoules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPoules): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPoules(): void {
    this.eventSubscriber = this.eventManager.subscribe('poulesListModification', () => this.loadAll());
  }

  delete(poules: IPoules): void {
    const modalRef = this.modalService.open(PoulesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.poules = poules;
  }
}
