import { IFiles } from 'app/shared/model/files.model';

export interface IRubrique {
  id?: number;
  name?: string;
  details?: any;
  imageContentType?: string;
  image?: any;
  files?: IFiles[];
  prestationServiceId?: number;
  competitionServicesOfferId?: number;
}

export class Rubrique implements IRubrique {
  constructor(
    public id?: number,
    public name?: string,
    public details?: any,
    public imageContentType?: string,
    public image?: any,
    public files?: IFiles[],
    public prestationServiceId?: number,
    public competitionServicesOfferId?: number
  ) {}
}
