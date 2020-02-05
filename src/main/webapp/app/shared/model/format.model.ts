export interface IFormat {
  id?: number;
  formatName?: string;
  winerQty?: number;
  phasePhaseName?: string;
  phaseId?: number;
  competitionId?: number;
}

export class Format implements IFormat {
  constructor(
    public id?: number,
    public formatName?: string,
    public winerQty?: number,
    public phasePhaseName?: string,
    public phaseId?: number,
    public competitionId?: number
  ) {}
}
