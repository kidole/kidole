export interface IDelegationMembers {
  id?: number;
  delegationMembersState?: string;
  delegationMembersCode?: string;
  delegationMembersDetail?: any;
  userFirstName?: string;
  userId?: number;
}

export class DelegationMembers implements IDelegationMembers {
  constructor(
    public id?: number,
    public delegationMembersState?: string,
    public delegationMembersCode?: string,
    public delegationMembersDetail?: any,
    public userFirstName?: string,
    public userId?: number
  ) {}
}
