import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetitionservicejoined } from 'app/shared/model/competitionservicejoined.model';
import { CompetitionservicejoinedService } from './competitionservicejoined.service';
import { CompetitionservicejoinedDeleteDialogComponent } from './competitionservicejoined-delete-dialog.component';

@Component({
  selector: 'jhi-competitionservicejoined',
  templateUrl: './competitionservicejoined.component.html'
})
export class CompetitionservicejoinedComponent implements OnInit, OnDestroy {
  competitionservicejoineds?: ICompetitionservicejoined[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitionservicejoinedService: CompetitionservicejoinedService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitionservicejoinedService.query().subscribe((res: HttpResponse<ICompetitionservicejoined[]>) => {
      this.competitionservicejoineds = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitionservicejoineds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetitionservicejoined): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetitionservicejoineds(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitionservicejoinedListModification', () => this.loadAll());
  }

  delete(competitionservicejoined: ICompetitionservicejoined): void {
    const modalRef = this.modalService.open(CompetitionservicejoinedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competitionservicejoined = competitionservicejoined;
  }
}
