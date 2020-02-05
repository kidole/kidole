import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMatchSheet } from 'app/shared/model/match-sheet.model';

@Component({
  selector: 'jhi-match-sheet-detail',
  templateUrl: './match-sheet-detail.component.html'
})
export class MatchSheetDetailComponent implements OnInit {
  matchSheet: IMatchSheet | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchSheet }) => {
      this.matchSheet = matchSheet;
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
