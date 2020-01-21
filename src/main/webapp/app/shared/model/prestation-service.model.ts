import { IFiles } from 'app/shared/model/files.model';
import { ILocalisation } from 'app/shared/model/localisation.model';

export interface IPrestationService {
  id?: number;
  name?: string;
  detail?: any;
  imageContentType?: string;
  image?: any;
  rubriqueId?: number;
  files?: IFiles[];
  localisations?: ILocalisation[];
  competitionservicejoinedId?: number;
}

export class PrestationService implements IPrestationService {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: any,
    public imageContentType?: string,
    public image?: any,
    public rubriqueId?: number,
    public files?: IFiles[],
    public localisations?: ILocalisation[],
    public competitionservicejoinedId?: number
  ) {}
}
