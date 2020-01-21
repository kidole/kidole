import { IUser } from 'app/core/user/user.model';

export interface ITeam {
  id?: number;
  name?: string;
  users?: IUser[];
  scoreId?: number;
  confrontationId?: number;
  poulesId?: number;
  delegationId?: number;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public name?: string,
    public users?: IUser[],
    public scoreId?: number,
    public confrontationId?: number,
    public poulesId?: number,
    public delegationId?: number
  ) {}
}
