import { Moment } from 'moment';
import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';

export interface IAccreditationStep {
  id?: number;
  debut?: Moment;
  fin?: Moment;
  numero?: number;
  type?: AccreditationList;
  competitionId?: number;
}

export class AccreditationStep implements IAccreditationStep {
  constructor(
    public id?: number,
    public debut?: Moment,
    public fin?: Moment,
    public numero?: number,
    public type?: AccreditationList,
    public competitionId?: number
  ) {}
}
