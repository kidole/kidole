import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';
import { RubriqueDeleteDialogComponent } from './rubrique-delete-dialog.component';

@Component({
  selector: 'jhi-rubrique',
  templateUrl: './rubrique.component.html'
})
export class RubriqueComponent implements OnInit, OnDestroy {
  rubriques?: IRubrique[];
  eventSubscriber?: Subscription;

  constructor(
    protected rubriqueService: RubriqueService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.rubriqueService.query().subscribe((res: HttpResponse<IRubrique[]>) => {
      this.rubriques = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRubriques();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRubrique): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInRubriques(): void {
    this.eventSubscriber = this.eventManager.subscribe('rubriqueListModification', () => this.loadAll());
  }

  delete(rubrique: IRubrique): void {
    const modalRef = this.modalService.open(RubriqueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rubrique = rubrique;
  }
}
