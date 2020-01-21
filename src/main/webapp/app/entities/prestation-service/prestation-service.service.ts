import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrestationService } from 'app/shared/model/prestation-service.model';

type EntityResponseType = HttpResponse<IPrestationService>;
type EntityArrayResponseType = HttpResponse<IPrestationService[]>;

@Injectable({ providedIn: 'root' })
export class PrestationServiceService {
  public resourceUrl = SERVER_API_URL + 'api/prestation-services';

  constructor(protected http: HttpClient) {}

  create(prestationService: IPrestationService): Observable<EntityResponseType> {
    return this.http.post<IPrestationService>(this.resourceUrl, prestationService, { observe: 'response' });
  }

  update(prestationService: IPrestationService): Observable<EntityResponseType> {
    return this.http.put<IPrestationService>(this.resourceUrl, prestationService, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrestationService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrestationService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
