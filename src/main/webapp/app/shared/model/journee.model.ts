import { IConfrontation } from 'app/shared/model/confrontation.model';

export interface IJournee {
  id?: number;
  name?: string;
  confrontations?: IConfrontation[];
  phaseId?: number;
}

export class Journee implements IJournee {
  constructor(public id?: number, public name?: string, public confrontations?: IConfrontation[], public phaseId?: number) {}
}
