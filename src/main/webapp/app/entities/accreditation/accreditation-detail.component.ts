import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccreditation } from 'app/shared/model/accreditation.model';

@Component({
  selector: 'jhi-accreditation-detail',
  templateUrl: './accreditation-detail.component.html'
})
export class AccreditationDetailComponent implements OnInit {
  accreditation: IAccreditation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditation }) => {
      this.accreditation = accreditation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
