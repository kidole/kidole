export interface IScore {
  id?: number;
  name?: string;
  scoreIndex?: number;
  value?: number;
  unite?: string;
  teamId?: number;
  confrontationId?: number;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public name?: string,
    public scoreIndex?: number,
    public value?: number,
    public unite?: string,
    public teamId?: number,
    public confrontationId?: number
  ) {}
}
