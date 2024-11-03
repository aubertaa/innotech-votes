package fr.aaubert.innotechvotesbackend.service.impl;

import fr.aaubert.innotechvotesbackend.exceptions.EntityDontExistException;
import fr.aaubert.innotechvotesbackend.model.Member;
import fr.aaubert.innotechvotesbackend.repository.MemberRepository;
import fr.aaubert.innotechvotesbackend.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void setVoteEnregistreForMemberId(Long id, Boolean voteEnregistre) {
        memberRepository.setVoteEnregistreForMemberId(id, voteEnregistre);
    }

    @Override
    public void createMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityDontExistException("Member with id " + id + " does not exist"));
    }

}
