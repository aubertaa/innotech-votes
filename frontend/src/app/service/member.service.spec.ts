import { MemberService } from './member.service';
import { ApiService } from './api.service';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

describe('MemberService', () => {
  let memberService: MemberService;
  let apiServiceMock: unknown;
  let routerMock: unknown;
  let toastServiceMock: unknown;

  beforeEach(() => {
    // Mock ApiService
    apiServiceMock = {
      getMembers: jest.fn(),
      setVoteEnregistreForMember: jest.fn()
    };

    // Mock the ToastService
    toastServiceMock = {
      addToast: jest.fn(),
    };

    // Initialize AuthService with the mocked dependencies
    memberService = new MemberService(
      apiServiceMock as ApiService,
      toastServiceMock as ToastService
    );
  });

  describe('getMembers', () => {
    it('should fetch members successfully and update the membersSubject', () => {
      const membersResponse = [
        { nom: 'nom1', prenom: 'prenom1', date_de_naissance: new Date(), voteEnregistre: true },
        { nom: 'nom2', prenom: 'prenom2', date_de_naissance: new Date(), voteEnregistre: true },
      ];

      (apiServiceMock as any).getMembers.mockReturnValue(of(membersResponse));

      memberService.getMembers();

      expect((apiServiceMock as any).getMembers).toHaveBeenCalled();
      expect(memberService.membersSubject.value).toEqual(membersResponse);
    });

    it('should handle error when fetching members', () => {
      const errorResponse = new HttpErrorResponse({
        error: 'test error',
        status: 500,
      });

      (apiServiceMock as any).getMembers.mockReturnValue(throwError(() => errorResponse));

      memberService.getMembers();

      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Members not fetched !');
      expect(memberService.membersSubject.value).toEqual([]);
    });
  });

  describe('setVoteEnregistreForMember', () => {
    it('should set voteEnregistre for a member successfully', () => {
      const memberId = 1;
      const voteEnregistre = true;

      (apiServiceMock as any).setVoteEnregistreForMember.mockReturnValue(of({}));
      (apiServiceMock as any).getMembers.mockReturnValue(of({}));

      memberService.setVoteEnregistreForMember(memberId, voteEnregistre);

      expect((apiServiceMock as any).setVoteEnregistreForMember).toHaveBeenCalledWith(memberId, voteEnregistre);
      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Vote enregistré !');
    });

    it('should handle error when setting voteEnregistre for a member', () => {
      const memberId = 1;
      const voteEnregistre = true;
      const errorResponse = new HttpErrorResponse({
        error: 'test error',
        status: 500,
      });

      (apiServiceMock as any).setVoteEnregistreForMember.mockReturnValue(throwError(() => errorResponse));

      memberService.setVoteEnregistreForMember(memberId, voteEnregistre);

      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Erreur à l\'enregistrement !');
    });
  });


});
