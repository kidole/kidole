import { ITeam } from 'app/shared/model/team.model';

export interface IPoules {
  id?: number;
  name?: string;
  teams?: ITeam[];
}

export class Poules implements IPoules {
  constructor(public id?: number, public name?: string, public teams?: ITeam[]) {}
}
