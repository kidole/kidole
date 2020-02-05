import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IConfrontation } from 'app/shared/model/confrontation.model';

type EntityResponseType = HttpResponse<IConfrontation>;
type EntityArrayResponseType = HttpResponse<IConfrontation[]>;

@Injectable({ providedIn: 'root' })
export class ConfrontationService {
  public resourceUrl = SERVER_API_URL + 'api/confrontations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/confrontations';

  constructor(protected http: HttpClient) {}

  create(confrontation: IConfrontation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(confrontation);
    return this.http
      .post<IConfrontation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(confrontation: IConfrontation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(confrontation);
    return this.http
      .put<IConfrontation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConfrontation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConfrontation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConfrontation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(confrontation: IConfrontation): IConfrontation {
    const copy: IConfrontation = Object.assign({}, confrontation, {
      startDate: confrontation.startDate && confrontation.startDate.isValid() ? confrontation.startDate.toJSON() : undefined,
      endDate: confrontation.endDate && confrontation.endDate.isValid() ? confrontation.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((confrontation: IConfrontation) => {
        confrontation.startDate = confrontation.startDate ? moment(confrontation.startDate) : undefined;
        confrontation.endDate = confrontation.endDate ? moment(confrontation.endDate) : undefined;
      });
    }
    return res;
  }
}
