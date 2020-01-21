import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';
import { AccreditationStepService } from './accreditation-step.service';
import { AccreditationStepDeleteDialogComponent } from './accreditation-step-delete-dialog.component';

@Component({
  selector: 'jhi-accreditation-step',
  templateUrl: './accreditation-step.component.html'
})
export class AccreditationStepComponent implements OnInit, OnDestroy {
  accreditationSteps?: IAccreditationStep[];
  eventSubscriber?: Subscription;

  constructor(
    protected accreditationStepService: AccreditationStepService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.accreditationStepService.query().subscribe((res: HttpResponse<IAccreditationStep[]>) => {
      this.accreditationSteps = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccreditationSteps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccreditationStep): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccreditationSteps(): void {
    this.eventSubscriber = this.eventManager.subscribe('accreditationStepListModification', () => this.loadAll());
  }

  delete(accreditationStep: IAccreditationStep): void {
    const modalRef = this.modalService.open(AccreditationStepDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accreditationStep = accreditationStep;
  }
}
