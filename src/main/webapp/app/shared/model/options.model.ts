export interface IOptions {
  id?: number;
  name?: string;
  value1?: boolean;
  value2?: number;
  competitionId?: number;
}

export class Options implements IOptions {
  constructor(public id?: number, public name?: string, public value1?: boolean, public value2?: number, public competitionId?: number) {
    this.value1 = this.value1 || false;
  }
}
