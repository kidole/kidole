import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from './competition.service';
import { CompetitionDeleteDialogComponent } from './competition-delete-dialog.component';

@Component({
  selector: 'jhi-competition',
  templateUrl: './competition.component.html'
})
export class CompetitionComponent implements OnInit, OnDestroy {
  competitions?: ICompetition[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitionService: CompetitionService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitionService.query().subscribe((res: HttpResponse<ICompetition[]>) => {
      this.competitions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCompetitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitionListModification', () => this.loadAll());
  }

  delete(competition: ICompetition): void {
    const modalRef = this.modalService.open(CompetitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
  }
}
