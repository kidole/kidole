import { Moment } from 'moment';
import { IScore } from 'app/shared/model/score.model';
import { ITeam } from 'app/shared/model/team.model';

export interface IConfrontation {
  id?: number;
  name?: string;
  debut?: Moment;
  fin?: Moment;
  details?: any;
  matchSheetId?: number;
  localisationId?: number;
  scores?: IScore[];
  teams?: ITeam[];
  journeeId?: number;
}

export class Confrontation implements IConfrontation {
  constructor(
    public id?: number,
    public name?: string,
    public debut?: Moment,
    public fin?: Moment,
    public details?: any,
    public matchSheetId?: number,
    public localisationId?: number,
    public scores?: IScore[],
    public teams?: ITeam[],
    public journeeId?: number
  ) {}
}
