import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDelegationMembers } from 'app/shared/model/delegation-members.model';

type EntityResponseType = HttpResponse<IDelegationMembers>;
type EntityArrayResponseType = HttpResponse<IDelegationMembers[]>;

@Injectable({ providedIn: 'root' })
export class DelegationMembersService {
  public resourceUrl = SERVER_API_URL + 'api/delegation-members';

  constructor(protected http: HttpClient) {}

  create(delegationMembers: IDelegationMembers): Observable<EntityResponseType> {
    return this.http.post<IDelegationMembers>(this.resourceUrl, delegationMembers, { observe: 'response' });
  }

  update(delegationMembers: IDelegationMembers): Observable<EntityResponseType> {
    return this.http.put<IDelegationMembers>(this.resourceUrl, delegationMembers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDelegationMembers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDelegationMembers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
