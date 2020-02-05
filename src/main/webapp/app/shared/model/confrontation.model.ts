import { Moment } from 'moment';
import { IScore } from 'app/shared/model/score.model';
import { ITeam } from 'app/shared/model/team.model';

export interface IConfrontation {
  id?: number;
  confrontationName?: string;
  startDate?: Moment;
  endDate?: Moment;
  confrontationDetails?: any;
  matchsheetMatchSheetName?: string;
  matchsheetId?: number;
  localisationLocalisationName?: string;
  localisationId?: number;
  scores?: IScore[];
  teams?: ITeam[];
  journeeId?: number;
}

export class Confrontation implements IConfrontation {
  constructor(
    public id?: number,
    public confrontationName?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public confrontationDetails?: any,
    public matchsheetMatchSheetName?: string,
    public matchsheetId?: number,
    public localisationLocalisationName?: string,
    public localisationId?: number,
    public scores?: IScore[],
    public teams?: ITeam[],
    public journeeId?: number
  ) {}
}
