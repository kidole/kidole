import { ITeam } from 'app/shared/model/team.model';

export interface IDelegation {
  id?: number;
  delegationName?: string;
  delegationCountry?: string;
  delegationLocality?: string;
  delegationCode?: string;
  delegateMemberDelegationMembersCode?: string;
  delegateMemberId?: number;
  teams?: ITeam[];
}

export class Delegation implements IDelegation {
  constructor(
    public id?: number,
    public delegationName?: string,
    public delegationCountry?: string,
    public delegationLocality?: string,
    public delegationCode?: string,
    public delegateMemberDelegationMembersCode?: string,
    public delegateMemberId?: number,
    public teams?: ITeam[]
  ) {}
}
