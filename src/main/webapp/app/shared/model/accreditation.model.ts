import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';
import { AccreditationState } from 'app/shared/model/enumerations/accreditation-state.model';

export interface IAccreditation {
  id?: number;
  name?: AccreditationList;
  status?: AccreditationState;
  details?: string;
  competitionId?: number;
}

export class Accreditation implements IAccreditation {
  constructor(
    public id?: number,
    public name?: AccreditationList,
    public status?: AccreditationState,
    public details?: string,
    public competitionId?: number
  ) {}
}
