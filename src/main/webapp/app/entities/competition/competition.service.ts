import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetition } from 'app/shared/model/competition.model';

type EntityResponseType = HttpResponse<ICompetition>;
type EntityArrayResponseType = HttpResponse<ICompetition[]>;

@Injectable({ providedIn: 'root' })
export class CompetitionService {
  public resourceUrl = SERVER_API_URL + 'api/competitions';

  constructor(protected http: HttpClient) {}

  create(competition: ICompetition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(competition);
    return this.http
      .post<ICompetition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(competition: ICompetition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(competition);
    return this.http
      .put<ICompetition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompetition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompetition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(competition: ICompetition): ICompetition {
    const copy: ICompetition = Object.assign({}, competition, {
      debut: competition.debut && competition.debut.isValid() ? competition.debut.toJSON() : undefined,
      fin: competition.fin && competition.fin.isValid() ? competition.fin.toJSON() : undefined,
      dateLimit: competition.dateLimit && competition.dateLimit.isValid() ? competition.dateLimit.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.debut = res.body.debut ? moment(res.body.debut) : undefined;
      res.body.fin = res.body.fin ? moment(res.body.fin) : undefined;
      res.body.dateLimit = res.body.dateLimit ? moment(res.body.dateLimit) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((competition: ICompetition) => {
        competition.debut = competition.debut ? moment(competition.debut) : undefined;
        competition.fin = competition.fin ? moment(competition.fin) : undefined;
        competition.dateLimit = competition.dateLimit ? moment(competition.dateLimit) : undefined;
      });
    }
    return res;
  }
}
