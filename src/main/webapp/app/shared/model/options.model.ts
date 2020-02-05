export interface IOptions {
  id?: number;
  optionsName?: string;
  optionsValue1?: boolean;
  optionsValue2?: number;
  competitionCompetitionName?: string;
  competitionId?: number;
}

export class Options implements IOptions {
  constructor(
    public id?: number,
    public optionsName?: string,
    public optionsValue1?: boolean,
    public optionsValue2?: number,
    public competitionCompetitionName?: string,
    public competitionId?: number
  ) {
    this.optionsValue1 = this.optionsValue1 || false;
  }
}
