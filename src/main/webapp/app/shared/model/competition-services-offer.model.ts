import { IFiles } from 'app/shared/model/files.model';

export interface ICompetitionServicesOffer {
  id?: number;
  name?: string;
  details?: string;
  url?: string;
  rubriqueId?: number;
  files?: IFiles[];
  competitionId?: number;
}

export class CompetitionServicesOffer implements ICompetitionServicesOffer {
  constructor(
    public id?: number,
    public name?: string,
    public details?: string,
    public url?: string,
    public rubriqueId?: number,
    public files?: IFiles[],
    public competitionId?: number
  ) {}
}
