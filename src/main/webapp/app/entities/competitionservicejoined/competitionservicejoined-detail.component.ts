import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitionservicejoined } from 'app/shared/model/competitionservicejoined.model';

@Component({
  selector: 'jhi-competitionservicejoined-detail',
  templateUrl: './competitionservicejoined-detail.component.html'
})
export class CompetitionservicejoinedDetailComponent implements OnInit {
  competitionservicejoined: ICompetitionservicejoined | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitionservicejoined }) => {
      this.competitionservicejoined = competitionservicejoined;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
