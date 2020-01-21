export interface IFormat {
  id?: number;
  name?: string;
  winerQty?: number;
  phaseId?: number;
  competitionId?: number;
}

export class Format implements IFormat {
  constructor(public id?: number, public name?: string, public winerQty?: number, public phaseId?: number, public competitionId?: number) {}
}
