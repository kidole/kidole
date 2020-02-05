import { IFiles } from 'app/shared/model/files.model';

export interface ICompetitionServicesOffer {
  id?: number;
  competitionServicesOfferName?: string;
  competitionServicesOfferDetail?: any;
  competitionServicesOfferUrl?: string;
  rubricRubriqueName?: string;
  rubricId?: number;
  files?: IFiles[];
  competitionId?: number;
}

export class CompetitionServicesOffer implements ICompetitionServicesOffer {
  constructor(
    public id?: number,
    public competitionServicesOfferName?: string,
    public competitionServicesOfferDetail?: any,
    public competitionServicesOfferUrl?: string,
    public rubricRubriqueName?: string,
    public rubricId?: number,
    public files?: IFiles[],
    public competitionId?: number
  ) {}
}
