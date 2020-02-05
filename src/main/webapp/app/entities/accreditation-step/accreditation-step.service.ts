import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAccreditationStep } from 'app/shared/model/accreditation-step.model';

type EntityResponseType = HttpResponse<IAccreditationStep>;
type EntityArrayResponseType = HttpResponse<IAccreditationStep[]>;

@Injectable({ providedIn: 'root' })
export class AccreditationStepService {
  public resourceUrl = SERVER_API_URL + 'api/accreditation-steps';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/accreditation-steps';

  constructor(protected http: HttpClient) {}

  create(accreditationStep: IAccreditationStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accreditationStep);
    return this.http
      .post<IAccreditationStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accreditationStep: IAccreditationStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accreditationStep);
    return this.http
      .put<IAccreditationStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccreditationStep>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccreditationStep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccreditationStep[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(accreditationStep: IAccreditationStep): IAccreditationStep {
    const copy: IAccreditationStep = Object.assign({}, accreditationStep, {
      startTime: accreditationStep.startTime && accreditationStep.startTime.isValid() ? accreditationStep.startTime.toJSON() : undefined,
      endTime: accreditationStep.endTime && accreditationStep.endTime.isValid() ? accreditationStep.endTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? moment(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accreditationStep: IAccreditationStep) => {
        accreditationStep.startTime = accreditationStep.startTime ? moment(accreditationStep.startTime) : undefined;
        accreditationStep.endTime = accreditationStep.endTime ? moment(accreditationStep.endTime) : undefined;
      });
    }
    return res;
  }
}
