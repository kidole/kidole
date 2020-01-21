import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccreditation } from 'app/shared/model/accreditation.model';

type EntityResponseType = HttpResponse<IAccreditation>;
type EntityArrayResponseType = HttpResponse<IAccreditation[]>;

@Injectable({ providedIn: 'root' })
export class AccreditationService {
  public resourceUrl = SERVER_API_URL + 'api/accreditations';

  constructor(protected http: HttpClient) {}

  create(accreditation: IAccreditation): Observable<EntityResponseType> {
    return this.http.post<IAccreditation>(this.resourceUrl, accreditation, { observe: 'response' });
  }

  update(accreditation: IAccreditation): Observable<EntityResponseType> {
    return this.http.put<IAccreditation>(this.resourceUrl, accreditation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccreditation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccreditation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
