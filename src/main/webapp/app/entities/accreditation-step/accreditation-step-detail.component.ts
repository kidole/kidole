import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';

@Component({
  selector: 'jhi-accreditation-step-detail',
  templateUrl: './accreditation-step-detail.component.html'
})
export class AccreditationStepDetailComponent implements OnInit {
  accreditationStep: IAccreditationStep | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditationStep }) => {
      this.accreditationStep = accreditationStep;
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
