export interface ICategory {
  id?: number;
  categoryName?: string;
  yearlimit?: number;
  teamLimitNumb?: number;
  participantLimitByteam?: number;
  categoryRule?: any;
  disciplineDisciplineName?: string;
  disciplineId?: number;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public categoryName?: string,
    public yearlimit?: number,
    public teamLimitNumb?: number,
    public participantLimitByteam?: number,
    public categoryRule?: any,
    public disciplineDisciplineName?: string,
    public disciplineId?: number
  ) {}
}
