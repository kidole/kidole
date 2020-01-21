import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJournee } from 'app/shared/model/journee.model';

type EntityResponseType = HttpResponse<IJournee>;
type EntityArrayResponseType = HttpResponse<IJournee[]>;

@Injectable({ providedIn: 'root' })
export class JourneeService {
  public resourceUrl = SERVER_API_URL + 'api/journees';

  constructor(protected http: HttpClient) {}

  create(journee: IJournee): Observable<EntityResponseType> {
    return this.http.post<IJournee>(this.resourceUrl, journee, { observe: 'response' });
  }

  update(journee: IJournee): Observable<EntityResponseType> {
    return this.http.put<IJournee>(this.resourceUrl, journee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJournee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJournee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
