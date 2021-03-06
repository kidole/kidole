import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IOptions } from 'app/shared/model/options.model';

type EntityResponseType = HttpResponse<IOptions>;
type EntityArrayResponseType = HttpResponse<IOptions[]>;

@Injectable({ providedIn: 'root' })
export class OptionsService {
  public resourceUrl = SERVER_API_URL + 'api/options';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/options';

  constructor(protected http: HttpClient) {}

  create(options: IOptions): Observable<EntityResponseType> {
    return this.http.post<IOptions>(this.resourceUrl, options, { observe: 'response' });
  }

  update(options: IOptions): Observable<EntityResponseType> {
    return this.http.put<IOptions>(this.resourceUrl, options, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptions[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
