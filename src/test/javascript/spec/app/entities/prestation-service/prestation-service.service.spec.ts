import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PrestationServiceService } from 'app/entities/prestation-service/prestation-service.service';
import { IPrestationService, PrestationService } from 'app/shared/model/prestation-service.model';
import { ServicesState } from 'app/shared/model/enumerations/services-state.model';

describe('Service Tests', () => {
  describe('PrestationService Service', () => {
    let injector: TestBed;
    let service: PrestationServiceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrestationService;
    let expectedResult: IPrestationService | IPrestationService[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PrestationServiceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PrestationService(0, 'AAAAAAA', ServicesState.ACCEPT, 'AAAAAAA', 'image/png', 'AAAAAAA');
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

      it('should create a PrestationService', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PrestationService())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PrestationService', () => {
        const returnedFromService = Object.assign(
          {
            prestationServiceName: 'BBBBBB',
            prestationServiceNameState: 'BBBBBB',
            prestationServiceNameDetail: 'BBBBBB',
            prestationServiceNameImage: 'BBBBBB'
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

      it('should return a list of PrestationService', () => {
        const returnedFromService = Object.assign(
          {
            prestationServiceName: 'BBBBBB',
            prestationServiceNameState: 'BBBBBB',
            prestationServiceNameDetail: 'BBBBBB',
            prestationServiceNameImage: 'BBBBBB'
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

      it('should delete a PrestationService', () => {
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
