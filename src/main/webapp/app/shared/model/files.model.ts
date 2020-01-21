export interface IFiles {
  id?: number;
  name?: string;
  type?: string;
  publique?: string;
  contentContentType?: string;
  content?: any;
  competitionServicesOfferId?: number;
  prestationServiceId?: number;
  rubriqueId?: number;
  competitionId?: number;
}

export class Files implements IFiles {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public publique?: string,
    public contentContentType?: string,
    public content?: any,
    public competitionServicesOfferId?: number,
    public prestationServiceId?: number,
    public rubriqueId?: number,
    public competitionId?: number
  ) {}
}
