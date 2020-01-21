import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';

@Component({
  selector: 'jhi-accreditation-step-detail',
  templateUrl: './accreditation-step-detail.component.html'
})
export class AccreditationStepDetailComponent implements OnInit {
  accreditationStep: IAccreditationStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditationStep }) => {
      this.accreditationStep = accreditationStep;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
