export interface ITeam {
  id?: number;
  teamName?: string;
  userFirstName?: string;
  userId?: number;
  confrontationId?: number;
  poulesId?: number;
  delegationId?: number;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public teamName?: string,
    public userFirstName?: string,
    public userId?: number,
    public confrontationId?: number,
    public poulesId?: number,
    public delegationId?: number
  ) {}
}
