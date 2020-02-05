import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IFormat } from 'app/shared/model/format.model';

type EntityResponseType = HttpResponse<IFormat>;
type EntityArrayResponseType = HttpResponse<IFormat[]>;

@Injectable({ providedIn: 'root' })
export class FormatService {
  public resourceUrl = SERVER_API_URL + 'api/formats';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/formats';

  constructor(protected http: HttpClient) {}

  create(format: IFormat): Observable<EntityResponseType> {
    return this.http.post<IFormat>(this.resourceUrl, format, { observe: 'response' });
  }

  update(format: IFormat): Observable<EntityResponseType> {
    return this.http.put<IFormat>(this.resourceUrl, format, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormat[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
