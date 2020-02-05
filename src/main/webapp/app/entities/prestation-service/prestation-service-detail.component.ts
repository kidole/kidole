import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrestationService } from 'app/shared/model/prestation-service.model';

@Component({
  selector: 'jhi-prestation-service-detail',
  templateUrl: './prestation-service-detail.component.html'
})
export class PrestationServiceDetailComponent implements OnInit {
  prestationService: IPrestationService | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prestationService }) => {
      this.prestationService = prestationService;
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
