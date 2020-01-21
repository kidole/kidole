import { IUser } from 'app/core/user/user.model';

export interface IDelegationMembers {
  id?: number;
  state?: string;
  code1?: string;
  detail?: string;
  users?: IUser[];
  delegationId?: number;
}

export class DelegationMembers implements IDelegationMembers {
  constructor(
    public id?: number,
    public state?: string,
    public code1?: string,
    public detail?: string,
    public users?: IUser[],
    public delegationId?: number
  ) {}
}
