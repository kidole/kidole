import { Moment } from 'moment';
import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';

export interface IAccreditationStep {
  id?: number;
  startTime?: Moment;
  endTime?: Moment;
  accreditationStepnumber?: number;
  accreditationType?: AccreditationList;
  isPublic?: boolean;
  uri?: boolean;
  fields?: any;
  competitionId?: number;
}

export class AccreditationStep implements IAccreditationStep {
  constructor(
    public id?: number,
    public startTime?: Moment,
    public endTime?: Moment,
    public accreditationStepnumber?: number,
    public accreditationType?: AccreditationList,
    public isPublic?: boolean,
    public uri?: boolean,
    public fields?: any,
    public competitionId?: number
  ) {
    this.isPublic = this.isPublic || false;
    this.uri = this.uri || false;
  }
}
