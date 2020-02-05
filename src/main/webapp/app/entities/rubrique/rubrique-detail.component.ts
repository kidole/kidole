import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRubrique } from 'app/shared/model/rubrique.model';

@Component({
  selector: 'jhi-rubrique-detail',
  templateUrl: './rubrique-detail.component.html'
})
export class RubriqueDetailComponent implements OnInit {
  rubrique: IRubrique | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rubrique }) => {
      this.rubrique = rubrique;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
