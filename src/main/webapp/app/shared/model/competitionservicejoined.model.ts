import { ServicesState } from 'app/shared/model/enumerations/services-state.model';

export interface ICompetitionservicejoined {
  id?: number;
  state?: ServicesState;
  details?: string;
  prestationServiceId?: number;
  competitionId?: number;
}

export class Competitionservicejoined implements ICompetitionservicejoined {
  constructor(
    public id?: number,
    public state?: ServicesState,
    public details?: string,
    public prestationServiceId?: number,
    public competitionId?: number
  ) {}
}
