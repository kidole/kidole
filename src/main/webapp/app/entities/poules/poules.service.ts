import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPoules } from 'app/shared/model/poules.model';

type EntityResponseType = HttpResponse<IPoules>;
type EntityArrayResponseType = HttpResponse<IPoules[]>;

@Injectable({ providedIn: 'root' })
export class PoulesService {
  public resourceUrl = SERVER_API_URL + 'api/poules';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/poules';

  constructor(protected http: HttpClient) {}

  create(poules: IPoules): Observable<EntityResponseType> {
    return this.http.post<IPoules>(this.resourceUrl, poules, { observe: 'response' });
  }

  update(poules: IPoules): Observable<EntityResponseType> {
    return this.http.put<IPoules>(this.resourceUrl, poules, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoules>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoules[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoules[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
