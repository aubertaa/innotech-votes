import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ToastService } from './toast.service';
import { BehaviorSubject } from 'rxjs';

export interface Member {
  id?: number;
  nom: string;
  prenom: string;
  date_de_naissance: Date;
  voteEnregistre: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class MemberService {

  membersSubject = new BehaviorSubject<Member[]>([]);
  members$ = this.membersSubject.asObservable();

  constructor(
    private apiService: ApiService,
    private toastService: ToastService
  ) { }

  setVoteEnregistreForMember (memberId: number, voteEnregistre: boolean) {
    this.apiService.setVoteEnregistreForMember(memberId, voteEnregistre).subscribe({
      next: (res) => {
        console.log('res: ' + res);
        this.getMembers(); // Refresh the members list
        this.toastService.addToast('Vote enregistré !');
      },
      error: (error) => {
        this.toastService.addToast('Erreur à l\'enregistrement !');
      },
    });
  }

  getMembers () {
    this.apiService.getMembers().subscribe({
      next: (res) => {
        console.log('res: ' + res);
        this.membersSubject.next(res); // Update the members Subject with the fetched items
      },
      error: (error) => {
        this.toastService.addToast('Members not fetched !');
      },
    });
  }
}
