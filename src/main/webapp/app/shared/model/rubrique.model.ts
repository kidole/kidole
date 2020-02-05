import { IFiles } from 'app/shared/model/files.model';

export interface IRubrique {
  id?: number;
  rubriqueName?: string;
  rubriqueDetails?: any;
  rubriqueImageContentType?: string;
  rubriqueImage?: any;
  files?: IFiles[];
}

export class Rubrique implements IRubrique {
  constructor(
    public id?: number,
    public rubriqueName?: string,
    public rubriqueDetails?: any,
    public rubriqueImageContentType?: string,
    public rubriqueImage?: any,
    public files?: IFiles[]
  ) {}
}
