import { IConfrontation } from 'app/shared/model/confrontation.model';

export interface IJournee {
  id?: number;
  journeeName?: string;
  confrontations?: IConfrontation[];
  phaseId?: number;
}

export class Journee implements IJournee {
  constructor(public id?: number, public journeeName?: string, public confrontations?: IConfrontation[], public phaseId?: number) {}
}
