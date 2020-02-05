import { ITeam } from 'app/shared/model/team.model';

export interface IPoules {
  id?: number;
  poulesName?: string;
  teams?: ITeam[];
}

export class Poules implements IPoules {
  constructor(public id?: number, public poulesName?: string, public teams?: ITeam[]) {}
}
