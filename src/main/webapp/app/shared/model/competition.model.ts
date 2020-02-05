import { Moment } from 'moment';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { IAccreditation } from 'app/shared/model/accreditation.model';
import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';
import { IFormat } from 'app/shared/model/format.model';
import { IFiles } from 'app/shared/model/files.model';

export interface ICompetition {
  id?: number;
  competitionName?: string;
  start?: Moment;
  end?: Moment;
  dateLimit?: Moment;
  detail?: any;
  ruleContentType?: string;
  rule?: any;
  localises?: ILocalisation[];
  accreditations?: IAccreditation[];
  competitionServices?: ICompetitionServicesOffer[];
  accreditationSteps?: IAccreditationStep[];
  formats?: IFormat[];
  files?: IFiles[];
  userFirstName?: string;
  userId?: number;
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public competitionName?: string,
    public start?: Moment,
    public end?: Moment,
    public dateLimit?: Moment,
    public detail?: any,
    public ruleContentType?: string,
    public rule?: any,
    public localises?: ILocalisation[],
    public accreditations?: IAccreditation[],
    public competitionServices?: ICompetitionServicesOffer[],
    public accreditationSteps?: IAccreditationStep[],
    public formats?: IFormat[],
    public files?: IFiles[],
    public userFirstName?: string,
    public userId?: number
  ) {}
}
