import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICompetitionServicesOffer } from 'app/shared/model/competition-services-offer.model';

type EntityResponseType = HttpResponse<ICompetitionServicesOffer>;
type EntityArrayResponseType = HttpResponse<ICompetitionServicesOffer[]>;

@Injectable({ providedIn: 'root' })
export class CompetitionServicesOfferService {
  public resourceUrl = SERVER_API_URL + 'api/competition-services-offers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/competition-services-offers';

  constructor(protected http: HttpClient) {}

  create(competitionServicesOffer: ICompetitionServicesOffer): Observable<EntityResponseType> {
    return this.http.post<ICompetitionServicesOffer>(this.resourceUrl, competitionServicesOffer, { observe: 'response' });
  }

  update(competitionServicesOffer: ICompetitionServicesOffer): Observable<EntityResponseType> {
    return this.http.put<ICompetitionServicesOffer>(this.resourceUrl, competitionServicesOffer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetitionServicesOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitionServicesOffer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitionServicesOffer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
