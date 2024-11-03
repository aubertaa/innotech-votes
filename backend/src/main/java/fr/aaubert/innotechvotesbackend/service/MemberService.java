package fr.aaubert.innotechvotesbackend.service;

import fr.aaubert.innotechvotesbackend.model.Member;

import java.util.List;

public interface MemberService {

    List<Member> getAllMembers();

    void setVoteEnregistreForMemberId(Long id, Boolean voteEnregistre);

    void createMember(Member member);

    Member getMemberById(Long id);
}
