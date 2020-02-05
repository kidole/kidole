import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';
import { AccreditationState } from 'app/shared/model/enumerations/accreditation-state.model';

export interface IAccreditation {
  id?: number;
  accreditationName?: AccreditationList;
  firstName?: string;
  lastName?: string;
  accreditationEmail?: string;
  accreditationStatus?: AccreditationState;
  accreditationDetail?: any;
  competitionId?: number;
}

export class Accreditation implements IAccreditation {
  constructor(
    public id?: number,
    public accreditationName?: AccreditationList,
    public firstName?: string,
    public lastName?: string,
    public accreditationEmail?: string,
    public accreditationStatus?: AccreditationState,
    public accreditationDetail?: any,
    public competitionId?: number
  ) {}
}
