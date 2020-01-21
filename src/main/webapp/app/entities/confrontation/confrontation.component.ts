import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';
import { ConfrontationDeleteDialogComponent } from './confrontation-delete-dialog.component';

@Component({
  selector: 'jhi-confrontation',
  templateUrl: './confrontation.component.html'
})
export class ConfrontationComponent implements OnInit, OnDestroy {
  confrontations?: IConfrontation[];
  eventSubscriber?: Subscription;

  constructor(
    protected confrontationService: ConfrontationService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.confrontationService.query().subscribe((res: HttpResponse<IConfrontation[]>) => {
      this.confrontations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConfrontations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConfrontation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInConfrontations(): void {
    this.eventSubscriber = this.eventManager.subscribe('confrontationListModification', () => this.loadAll());
  }

  delete(confrontation: IConfrontation): void {
    const modalRef = this.modalService.open(ConfrontationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.confrontation = confrontation;
  }
}
