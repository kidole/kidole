import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';

export interface IProfile {
  id?: number;
  gender?: Gender;
  photoContentType?: string;
  photo?: any;
  isAccreditated?: boolean;
  accreditationtype?: AccreditationList;
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
  fonction?: string;
  titre?: string;
  residentCity?: string;
  pressID?: string;
  pressAgence?: string;
  bataillonRattachement?: string;
  socialDenomination?: string;
  locationBuilding?: string;
  userId?: number;
  userAccreditationId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public gender?: Gender,
    public photoContentType?: string,
    public photo?: any,
    public isAccreditated?: boolean,
    public accreditationtype?: AccreditationList,
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
    public fonction?: string,
    public titre?: string,
    public residentCity?: string,
    public pressID?: string,
    public pressAgence?: string,
    public bataillonRattachement?: string,
    public socialDenomination?: string,
    public locationBuilding?: string,
    public userId?: number,
    public userAccreditationId?: number
  ) {
    this.isAccreditated = this.isAccreditated || false;
  }
}
