import { ITeam } from 'app/shared/model/team.model';

export interface IDelegation {
  id?: number;
  name?: string;
  country?: string;
  locality?: string;
  code1?: string;
  delegationMembersId?: number;
  teams?: ITeam[];
}

export class Delegation implements IDelegation {
  constructor(
    public id?: number,
    public name?: string,
    public country?: string,
    public locality?: string,
    public code1?: string,
    public delegationMembersId?: number,
    public teams?: ITeam[]
  ) {}
}
