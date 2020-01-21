import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { AccreditationService } from 'app/entities/accreditation/accreditation.service';
import { IAccreditation, Accreditation } from 'app/shared/model/accreditation.model';
import { AccreditationList } from 'app/shared/model/enumerations/accreditation-list.model';
import { AccreditationState } from 'app/shared/model/enumerations/accreditation-state.model';

describe('Service Tests', () => {
  describe('Accreditation Service', () => {
    let injector: TestBed;
    let service: AccreditationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccreditation;
    let expectedResult: IAccreditation | IAccreditation[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AccreditationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Accreditation(0, AccreditationList.ATHLETE, AccreditationState.ACTIVE, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Accreditation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Accreditation())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Accreditation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            status: 'BBBBBB',
            details: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Accreditation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            status: 'BBBBBB',
            details: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Accreditation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
