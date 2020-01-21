export interface ICategory {
  id?: number;
  name?: string;
  yearlimit?: number;
  teamLimitNumb?: number;
  participantLimitByteam?: number;
  regles?: any;
  disciplineId?: number;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public name?: string,
    public yearlimit?: number,
    public teamLimitNumb?: number,
    public participantLimitByteam?: number,
    public regles?: any,
    public disciplineId?: number
  ) {}
}
