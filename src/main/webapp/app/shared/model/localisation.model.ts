export interface ILocalisation {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  country?: string;
  town?: string;
  region?: string;
  locality?: string;
  isSite?: boolean;
  confrontationId?: number;
  competitionId?: number;
  prestationServiceId?: number;
}

export class Localisation implements ILocalisation {
  constructor(
    public id?: number,
    public name?: string,
    public latitude?: number,
    public longitude?: number,
    public country?: string,
    public town?: string,
    public region?: string,
    public locality?: string,
    public isSite?: boolean,
    public confrontationId?: number,
    public competitionId?: number,
    public prestationServiceId?: number
  ) {
    this.isSite = this.isSite || false;
  }
}
