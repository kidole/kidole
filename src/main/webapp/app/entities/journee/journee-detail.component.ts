import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournee } from 'app/shared/model/journee.model';

@Component({
  selector: 'jhi-journee-detail',
  templateUrl: './journee-detail.component.html'
})
export class JourneeDetailComponent implements OnInit {
  journee: IJournee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journee }) => {
      this.journee = journee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
