import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptions } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';

@Component({
  templateUrl: './options-delete-dialog.component.html'
})
export class OptionsDeleteDialogComponent {
  options?: IOptions;

  constructor(protected optionsService: OptionsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optionsListModification');
      this.activeModal.close();
    });
  }
}
