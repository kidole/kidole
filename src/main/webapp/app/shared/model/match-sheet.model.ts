export interface IMatchSheet {
  id?: number;
  name?: string;
  resume?: string;
  isfirst?: boolean;
  confrontationId?: number;
}

export class MatchSheet implements IMatchSheet {
  constructor(public id?: number, public name?: string, public resume?: string, public isfirst?: boolean, public confrontationId?: number) {
    this.isfirst = this.isfirst || false;
  }
}
