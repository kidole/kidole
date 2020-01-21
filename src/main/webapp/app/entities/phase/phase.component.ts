import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';
import { PhaseDeleteDialogComponent } from './phase-delete-dialog.component';

@Component({
  selector: 'jhi-phase',
  templateUrl: './phase.component.html'
})
export class PhaseComponent implements OnInit, OnDestroy {
  phases?: IPhase[];
  eventSubscriber?: Subscription;

  constructor(protected phaseService: PhaseService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.phaseService.query().subscribe((res: HttpResponse<IPhase[]>) => {
      this.phases = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPhases(): void {
    this.eventSubscriber = this.eventManager.subscribe('phaseListModification', () => this.loadAll());
  }

  delete(phase: IPhase): void {
    const modalRef = this.modalService.open(PhaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phase = phase;
  }
}
