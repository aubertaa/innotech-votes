import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import {
  ApiService
} from './api.service';
import { HttpErrorResponse } from '@angular/common/http';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;
  const apiUrl = 'http://localhost:8081/api';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService],
    });

    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure no outstanding HTTP requests
  });

  describe('catchError', () => {
    it('should handle error', () => {
      const mockError = new HttpErrorResponse({
        status: 404,
        statusText: 'Not Found',
        error: { error: 'Error' },
      });

      service.catchError(mockError).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });
    });
  });

  describe('getMembers', () => {
    it('should retrieve members', () => {
      const mockMembers: string[] = ['Alice', 'Bob', 'Charlie'];

      service.getMembers().subscribe((members) => {
        expect(members).toBeDefined();
      });

      const req = httpMock.expectOne('http://localhost:8081/api/members');
      expect(req.request.method).toBe('GET');
      req.flush(mockMembers);
    });
  });

  describe('setVoteEnregistreForMember', () => {
    it('should set vote for a member', () => {
      const mockMemberId = 1;
      const mockVoteEnregistre = true;

      service.setVoteEnregistreForMember(mockMemberId, mockVoteEnregistre).subscribe((res) => {
        expect(res).toBeDefined();
      });

      const req = httpMock.expectOne('http://localhost:8081/api/member/enregistrer-vote?id=1&voteEnregistre=true');
      expect(req.request.method).toBe('POST');
      req.flush({});
    });
  });

});
