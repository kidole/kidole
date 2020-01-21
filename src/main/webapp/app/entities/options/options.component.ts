import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptions } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';
import { OptionsDeleteDialogComponent } from './options-delete-dialog.component';

@Component({
  selector: 'jhi-options',
  templateUrl: './options.component.html'
})
export class OptionsComponent implements OnInit, OnDestroy {
  options?: IOptions[];
  eventSubscriber?: Subscription;

  constructor(protected optionsService: OptionsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.optionsService.query().subscribe((res: HttpResponse<IOptions[]>) => {
      this.options = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOptions): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('optionsListModification', () => this.loadAll());
  }

  delete(options: IOptions): void {
    const modalRef = this.modalService.open(OptionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.options = options;
  }
}
