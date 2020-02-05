import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IProfile {
  id?: number;
  gender?: Gender;
  photoContentType?: string;
  photo?: any;
  dateOfBirth?: Moment;
  placeOfBbirth?: string;
  clubOrigin?: string;
  nationality?: string;
  height?: number;
  weight?: number;
  manuality?: string;
  nic?: string;
  phone?: string;
  discipline?: string;
  categorie?: string;
  teamName?: string;
  functionOn?: string;
  titleAs?: string;
  residentCity?: string;
  pressID?: string;
  pressAgence?: string;
  bataillonRattachement?: string;
  socialDenomination?: string;
  locationBuilding?: string;
  userFirstName?: string;
  userId?: number;
  accreditationAccreditationName?: string;
  accreditationId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public gender?: Gender,
    public photoContentType?: string,
    public photo?: any,
    public dateOfBirth?: Moment,
    public placeOfBbirth?: string,
    public clubOrigin?: string,
    public nationality?: string,
    public height?: number,
    public weight?: number,
    public manuality?: string,
    public nic?: string,
    public phone?: string,
    public discipline?: string,
    public categorie?: string,
    public teamName?: string,
    public functionOn?: string,
    public titleAs?: string,
    public residentCity?: string,
    public pressID?: string,
    public pressAgence?: string,
    public bataillonRattachement?: string,
    public socialDenomination?: string,
    public locationBuilding?: string,
    public userFirstName?: string,
    public userId?: number,
    public accreditationAccreditationName?: string,
    public accreditationId?: number
  ) {}
}
