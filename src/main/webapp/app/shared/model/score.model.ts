export interface IScore {
  id?: number;
  scoreName?: string;
  scoreIndex?: number;
  scoreValue?: number;
  scoreUnit?: string;
  teamTeamName?: string;
  teamId?: number;
  confrontationId?: number;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public scoreName?: string,
    public scoreIndex?: number,
    public scoreValue?: number,
    public scoreUnit?: string,
    public teamTeamName?: string,
    public teamId?: number,
    public confrontationId?: number
  ) {}
}
