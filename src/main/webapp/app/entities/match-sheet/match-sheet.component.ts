import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from './match-sheet.service';
import { MatchSheetDeleteDialogComponent } from './match-sheet-delete-dialog.component';

@Component({
  selector: 'jhi-match-sheet',
  templateUrl: './match-sheet.component.html'
})
export class MatchSheetComponent implements OnInit, OnDestroy {
  matchSheets?: IMatchSheet[];
  eventSubscriber?: Subscription;

  constructor(protected matchSheetService: MatchSheetService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.matchSheetService.query().subscribe((res: HttpResponse<IMatchSheet[]>) => {
      this.matchSheets = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMatchSheets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMatchSheet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMatchSheets(): void {
    this.eventSubscriber = this.eventManager.subscribe('matchSheetListModification', () => this.loadAll());
  }

  delete(matchSheet: IMatchSheet): void {
    const modalRef = this.modalService.open(MatchSheetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.matchSheet = matchSheet;
  }
}
