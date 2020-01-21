import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormat } from 'app/shared/model/format.model';
import { FormatService } from './format.service';
import { FormatDeleteDialogComponent } from './format-delete-dialog.component';

@Component({
  selector: 'jhi-format',
  templateUrl: './format.component.html'
})
export class FormatComponent implements OnInit, OnDestroy {
  formats?: IFormat[];
  eventSubscriber?: Subscription;

  constructor(protected formatService: FormatService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.formatService.query().subscribe((res: HttpResponse<IFormat[]>) => {
      this.formats = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFormats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFormat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFormats(): void {
    this.eventSubscriber = this.eventManager.subscribe('formatListModification', () => this.loadAll());
  }

  delete(format: IFormat): void {
    const modalRef = this.modalService.open(FormatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.format = format;
  }
}
