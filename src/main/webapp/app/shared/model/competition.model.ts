import { Moment } from 'moment';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { IAccreditation } from 'app/shared/model/accreditation.model';
import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';
import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';
import { ICompetitionservicejoined } from 'app/shared/model/competitionservicejoined.model';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { IOptions } from 'app/shared/model/options.model';
import { IFormat } from 'app/shared/model/format.model';
import { IFiles } from 'app/shared/model/files.model';

export interface ICompetition {
  id?: number;
  name?: string;
  debut?: Moment;
  fin?: Moment;
  dateLimit?: Moment;
  detail?: any;
  roleContentType?: string;
  role?: any;
  localisations?: ILocalisation[];
  accreditateUsers?: IAccreditation[];
  competitionServicesOffers?: ICompetitionServicesOffer[];
  accreditationSteps?: IAccreditationStep[];
  competitionservicejoineds?: ICompetitionservicejoined[];
  disciplines?: IDiscipline[];
  options?: IOptions[];
  formats?: IFormat[];
  files?: IFiles[];
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public name?: string,
    public debut?: Moment,
    public fin?: Moment,
    public dateLimit?: Moment,
    public detail?: any,
    public roleContentType?: string,
    public role?: any,
    public localisations?: ILocalisation[],
    public accreditateUsers?: IAccreditation[],
    public competitionServicesOffers?: ICompetitionServicesOffer[],
    public accreditationSteps?: IAccreditationStep[],
    public competitionservicejoineds?: ICompetitionservicejoined[],
    public disciplines?: IDiscipline[],
    public options?: IOptions[],
    public formats?: IFormat[],
    public files?: IFiles[]
  ) {}
}
