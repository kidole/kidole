export interface IMatchSheet {
  id?: number;
  matchSheetName?: string;
  matchSheetResume?: any;
  isfirst?: boolean;
}

export class MatchSheet implements IMatchSheet {
  constructor(public id?: number, public matchSheetName?: string, public matchSheetResume?: any, public isfirst?: boolean) {
    this.isfirst = this.isfirst || false;
  }
}
