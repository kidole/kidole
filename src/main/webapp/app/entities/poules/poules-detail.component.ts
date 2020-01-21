import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoules } from 'app/shared/model/poules.model';

@Component({
  selector: 'jhi-poules-detail',
  templateUrl: './poules-detail.component.html'
})
export class PoulesDetailComponent implements OnInit {
  poules: IPoules | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poules }) => {
      this.poules = poules;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
