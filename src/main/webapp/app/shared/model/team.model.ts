export interface ITeam {
  id?: number;
  name?: string;
  scoreId?: number;
  confrontationId?: number;
  poulesId?: number;
  delegationId?: number;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public name?: string,
    public scoreId?: number,
    public confrontationId?: number,
    public poulesId?: number,
    public delegationId?: number
  ) {}
}
