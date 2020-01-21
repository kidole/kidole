import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrestationService } from 'app/shared/model/prestation-service.model';
import { PrestationServiceService } from './prestation-service.service';
import { PrestationServiceDeleteDialogComponent } from './prestation-service-delete-dialog.component';

@Component({
  selector: 'jhi-prestation-service',
  templateUrl: './prestation-service.component.html'
})
export class PrestationServiceComponent implements OnInit, OnDestroy {
  prestationServices?: IPrestationService[];
  eventSubscriber?: Subscription;

  constructor(
    protected prestationServiceService: PrestationServiceService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.prestationServiceService.query().subscribe((res: HttpResponse<IPrestationService[]>) => {
      this.prestationServices = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrestationServices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrestationService): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPrestationServices(): void {
    this.eventSubscriber = this.eventManager.subscribe('prestationServiceListModification', () => this.loadAll());
  }

  delete(prestationService: IPrestationService): void {
    const modalRef = this.modalService.open(PrestationServiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prestationService = prestationService;
  }
}
