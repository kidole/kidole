import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormat } from 'app/shared/model/format.model';
import { FormatService } from './format.service';

@Component({
  templateUrl: './format-delete-dialog.component.html'
})
export class FormatDeleteDialogComponent {
  format?: IFormat;

  constructor(protected formatService: FormatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formatListModification');
      this.activeModal.close();
    });
  }
}
