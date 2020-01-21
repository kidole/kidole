import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatchSheet } from 'app/shared/model/match-sheet.model';

@Component({
  selector: 'jhi-match-sheet-detail',
  templateUrl: './match-sheet-detail.component.html'
})
export class MatchSheetDetailComponent implements OnInit {
  matchSheet: IMatchSheet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchSheet }) => {
      this.matchSheet = matchSheet;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
