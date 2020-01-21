import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatchSheet } from 'app/shared/model/match-sheet.model';
import { MatchSheetService } from './match-sheet.service';

@Component({
  templateUrl: './match-sheet-delete-dialog.component.html'
})
export class MatchSheetDeleteDialogComponent {
  matchSheet?: IMatchSheet;

  constructor(
    protected matchSheetService: MatchSheetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matchSheetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('matchSheetListModification');
      this.activeModal.close();
    });
  }
}
