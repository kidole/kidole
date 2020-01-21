import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';
import { LocalisationDeleteDialogComponent } from './localisation-delete-dialog.component';

@Component({
  selector: 'jhi-localisation',
  templateUrl: './localisation.component.html'
})
export class LocalisationComponent implements OnInit, OnDestroy {
  localisations?: ILocalisation[];
  eventSubscriber?: Subscription;

  constructor(
    protected localisationService: LocalisationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.localisationService.query().subscribe((res: HttpResponse<ILocalisation[]>) => {
      this.localisations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLocalisations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILocalisation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLocalisations(): void {
    this.eventSubscriber = this.eventManager.subscribe('localisationListModification', () => this.loadAll());
  }

  delete(localisation: ILocalisation): void {
    const modalRef = this.modalService.open(LocalisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localisation = localisation;
  }
}
