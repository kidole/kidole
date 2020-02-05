import { IJournee } from 'app/shared/model/journee.model';

export interface IPhase {
  id?: number;
  phaseName?: string;
  phaseNumber?: number;
  phaseDayNumber?: number;
  journnees?: IJournee[];
}

export class Phase implements IPhase {
  constructor(
    public id?: number,
    public phaseName?: string,
    public phaseNumber?: number,
    public phaseDayNumber?: number,
    public journnees?: IJournee[]
  ) {}
}
