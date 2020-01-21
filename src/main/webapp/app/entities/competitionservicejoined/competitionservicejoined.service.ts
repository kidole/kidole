import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetitionservicejoined } from 'app/shared/model/competitionservicejoined.model';

type EntityResponseType = HttpResponse<ICompetitionservicejoined>;
type EntityArrayResponseType = HttpResponse<ICompetitionservicejoined[]>;

@Injectable({ providedIn: 'root' })
export class CompetitionservicejoinedService {
  public resourceUrl = SERVER_API_URL + 'api/competitionservicejoineds';

  constructor(protected http: HttpClient) {}

  create(competitionservicejoined: ICompetitionservicejoined): Observable<EntityResponseType> {
    return this.http.post<ICompetitionservicejoined>(this.resourceUrl, competitionservicejoined, { observe: 'response' });
  }

  update(competitionservicejoined: ICompetitionservicejoined): Observable<EntityResponseType> {
    return this.http.put<ICompetitionservicejoined>(this.resourceUrl, competitionservicejoined, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetitionservicejoined>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitionservicejoined[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
