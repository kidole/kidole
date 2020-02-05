import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IConfrontation } from 'app/shared/model/confrontation.model';

@Component({
  selector: 'jhi-confrontation-detail',
  templateUrl: './confrontation-detail.component.html'
})
export class ConfrontationDetailComponent implements OnInit {
  confrontation: IConfrontation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ confrontation }) => {
      this.confrontation = confrontation;
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
