import { IJournee } from 'app/shared/model/journee.model';

export interface IPhase {
  id?: number;
  name?: string;
  numero?: number;
  dayNumber?: number;
  journees?: IJournee[];
  formatId?: number;
  disciplineId?: number;
}

export class Phase implements IPhase {
  constructor(
    public id?: number,
    public name?: string,
    public numero?: number,
    public dayNumber?: number,
    public journees?: IJournee[],
    public formatId?: number,
    public disciplineId?: number
  ) {}
}
