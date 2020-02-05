export interface IFiles {
  id?: number;
  fileName?: string;
  fileType?: string;
  filePublic?: boolean;
  fileToUploadContentType?: string;
  fileToUpload?: any;
  userFirstName?: string;
  userId?: number;
  competitionServicesOfferId?: number;
  prestationServiceId?: number;
  rubriqueId?: number;
  competitionId?: number;
}

export class Files implements IFiles {
  constructor(
    public id?: number,
    public fileName?: string,
    public fileType?: string,
    public filePublic?: boolean,
    public fileToUploadContentType?: string,
    public fileToUpload?: any,
    public userFirstName?: string,
    public userId?: number,
    public competitionServicesOfferId?: number,
    public prestationServiceId?: number,
    public rubriqueId?: number,
    public competitionId?: number
  ) {
    this.filePublic = this.filePublic || false;
  }
}
