import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('Profile Service', () => {
    let injector: TestBed;
    let service: ProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfile;
    let expectedResult: IProfile | IProfile[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Profile(
        0,
        Gender.MALE,
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Profile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .create(new Profile())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Profile', () => {
        const returnedFromService = Object.assign(
          {
            gender: 'BBBBBB',
            photo: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
            placeOfBbirth: 'BBBBBB',
            clubOrigin: 'BBBBBB',
            nationality: 'BBBBBB',
            height: 1,
            weight: 1,
            manuality: 'BBBBBB',
            nic: 'BBBBBB',
            phone: 'BBBBBB',
            discipline: 'BBBBBB',
            categorie: 'BBBBBB',
            teamName: 'BBBBBB',
            functionOn: 'BBBBBB',
            titleAs: 'BBBBBB',
            residentCity: 'BBBBBB',
            pressID: 'BBBBBB',
            pressAgence: 'BBBBBB',
            bataillonRattachement: 'BBBBBB',
            socialDenomination: 'BBBBBB',
            locationBuilding: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Profile', () => {
        const returnedFromService = Object.assign(
          {
            gender: 'BBBBBB',
            photo: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
            placeOfBbirth: 'BBBBBB',
            clubOrigin: 'BBBBBB',
            nationality: 'BBBBBB',
            height: 1,
            weight: 1,
            manuality: 'BBBBBB',
            nic: 'BBBBBB',
            phone: 'BBBBBB',
            discipline: 'BBBBBB',
            categorie: 'BBBBBB',
            teamName: 'BBBBBB',
            functionOn: 'BBBBBB',
            titleAs: 'BBBBBB',
            residentCity: 'BBBBBB',
            pressID: 'BBBBBB',
            pressAgence: 'BBBBBB',
            bataillonRattachement: 'BBBBBB',
            socialDenomination: 'BBBBBB',
            locationBuilding: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Profile', () => {
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
