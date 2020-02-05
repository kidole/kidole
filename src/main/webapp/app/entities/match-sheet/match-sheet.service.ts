import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMatchSheet } from 'app/shared/model/match-sheet.model';

type EntityResponseType = HttpResponse<IMatchSheet>;
type EntityArrayResponseType = HttpResponse<IMatchSheet[]>;

@Injectable({ providedIn: 'root' })
export class MatchSheetService {
  public resourceUrl = SERVER_API_URL + 'api/match-sheets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/match-sheets';

  constructor(protected http: HttpClient) {}

  create(matchSheet: IMatchSheet): Observable<EntityResponseType> {
    return this.http.post<IMatchSheet>(this.resourceUrl, matchSheet, { observe: 'response' });
  }

  update(matchSheet: IMatchSheet): Observable<EntityResponseType> {
    return this.http.put<IMatchSheet>(this.resourceUrl, matchSheet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatchSheet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchSheet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchSheet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
