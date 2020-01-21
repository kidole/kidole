import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetitionservicejoined } from 'app/shared/model/competitionservicejoined.model';
import { CompetitionservicejoinedService } from './competitionservicejoined.service';

@Component({
  templateUrl: './competitionservicejoined-delete-dialog.component.html'
})
export class CompetitionservicejoinedDeleteDialogComponent {
  competitionservicejoined?: ICompetitionservicejoined;

  constructor(
    protected competitionservicejoinedService: CompetitionservicejoinedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competitionservicejoinedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competitionservicejoinedListModification');
      this.activeModal.close();
    });
  }
}
