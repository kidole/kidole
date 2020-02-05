export interface ILocalisation {
  id?: number;
  localisationName?: string;
  localisationLatitude?: number;
  localisationLongitude?: number;
  localisationCountry?: string;
  localisationTown?: string;
  localisationRegion?: string;
  localisationLocality?: string;
  isSite?: boolean;
  competitionId?: number;
  prestationServiceId?: number;
}

export class Localisation implements ILocalisation {
  constructor(
    public id?: number,
    public localisationName?: string,
    public localisationLatitude?: number,
    public localisationLongitude?: number,
    public localisationCountry?: string,
    public localisationTown?: string,
    public localisationRegion?: string,
    public localisationLocality?: string,
    public isSite?: boolean,
    public competitionId?: number,
    public prestationServiceId?: number
  ) {
    this.isSite = this.isSite || false;
  }
}
