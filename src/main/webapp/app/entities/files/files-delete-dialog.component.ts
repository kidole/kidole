import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFiles } from 'app/shared/model/files.model';
import { FilesService } from './files.service';

@Component({
  templateUrl: './files-delete-dialog.component.html'
})
export class FilesDeleteDialogComponent {
  files?: IFiles;

  constructor(protected filesService: FilesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.filesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('filesListModification');
      this.activeModal.close();
    });
  }
}
