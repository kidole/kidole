import { IFiles } from 'app/shared/model/files.model';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { ServicesState } from 'app/shared/model/enumerations/services-state.model';

export interface IPrestationService {
  id?: number;
  prestationServiceName?: string;
  prestationServiceNameState?: ServicesState;
  prestationServiceNameDetail?: any;
  prestationServiceNameImageContentType?: string;
  prestationServiceNameImage?: any;
  rubriqueRubriqueName?: string;
  rubriqueId?: number;
  files?: IFiles[];
  localisations?: ILocalisation[];
  userFirstName?: string;
  userId?: number;
}

export class PrestationService implements IPrestationService {
  constructor(
    public id?: number,
    public prestationServiceName?: string,
    public prestationServiceNameState?: ServicesState,
    public prestationServiceNameDetail?: any,
    public prestationServiceNameImageContentType?: string,
    public prestationServiceNameImage?: any,
    public rubriqueRubriqueName?: string,
    public rubriqueId?: number,
    public files?: IFiles[],
    public localisations?: ILocalisation[],
    public userFirstName?: string,
    public userId?: number
  ) {}
}
