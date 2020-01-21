import { IPhase } from 'app/shared/model/phase.model';
import { ICategory } from 'app/shared/model/category.model';
import { GenderSex } from 'app/shared/model/enumerations/gender-sex.model';

export interface IDiscipline {
  id?: number;
  name?: string;
  sexGender?: GenderSex;
  phases?: IPhase[];
  categories?: ICategory[];
  competitionId?: number;
}

export class Discipline implements IDiscipline {
  constructor(
    public id?: number,
    public name?: string,
    public sexGender?: GenderSex,
    public phases?: IPhase[],
    public categories?: ICategory[],
    public competitionId?: number
  ) {}
}
