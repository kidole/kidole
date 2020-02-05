import { GenderSex } from 'app/shared/model/enumerations/gender-sex.model';

export interface IDiscipline {
  id?: number;
  disciplineName?: string;
  sexGender?: GenderSex;
  competitionCompetitionName?: string;
  competitionId?: number;
  phasePhaseName?: string;
  phaseId?: number;
}

export class Discipline implements IDiscipline {
  constructor(
    public id?: number,
    public disciplineName?: string,
    public sexGender?: GenderSex,
    public competitionCompetitionName?: string,
    public competitionId?: number,
    public phasePhaseName?: string,
    public phaseId?: number
  ) {}
}
