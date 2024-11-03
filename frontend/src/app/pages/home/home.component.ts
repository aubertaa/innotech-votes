import { Component, OnInit } from '@angular/core';
import { MemberService, Member } from '../../service/member.service';
import { Observable } from 'rxjs';

@Component({
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {

  members$: Observable<Member[]>;

  constructor(
    private memberService: MemberService
  ) {
    this.members$ = this.memberService.members$;
  }

  setVoteEnregistreForMember (member: Member, voteEnregistre: boolean) {
    this.memberService.setVoteEnregistreForMember(member.id ? member.id : 0, voteEnregistre);
  }

  ngOnInit (): void {
    this.memberService.getMembers();
  }

}
