import {
  HttpClient,
  HttpErrorResponse,
  HttpParams
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Member } from './member.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {


  private apiUrl = 'http://localhost:8081/api';
  constructor(private httpClient: HttpClient) { }

  catchError (error: HttpErrorResponse) {
    //alert(error.error.error);
    return throwError(() => error);
  }

  getMembers () {
    return this.httpClient
      .get<Member[]>(`${this.apiUrl}/members`)
      .pipe(catchError(this.catchError));
  }

  setVoteEnregistreForMember (memberId: number, voteEnregistre: boolean) {
    const params = new HttpParams()
      .set('id', memberId)
      .set('voteEnregistre', voteEnregistre);

    return this.httpClient
      .post<unknown>(`${this.apiUrl}/member/enregistrer-vote`, null, { params })
      .pipe(catchError(this.catchError));
  }

}
