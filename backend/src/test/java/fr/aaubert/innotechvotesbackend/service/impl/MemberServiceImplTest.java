package fr.aaubert.innotechvotesbackend.service.impl;

import fr.aaubert.innotechvotesbackend.controller.MemberController;
import fr.aaubert.innotechvotesbackend.exceptions.EntityDontExistException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.aaubert.innotechvotesbackend.model.Member;
import fr.aaubert.innotechvotesbackend.repository.MemberRepository;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberController memberController;

    @InjectMocks
    private MemberServiceImpl memberService;


    @Test
    void setVoteEnregistreForMemberId_withValidMemberId_updatesVoteEnregistre() {
        Long id = 1L;
        Boolean voteEnregistre = true;

        memberService.setVoteEnregistreForMemberId(id, voteEnregistre);

        verify(memberRepository, times(1)).setVoteEnregistreForMemberId(id, voteEnregistre);
    }


    @Test
    void testGetAllMembers() {
        // Test case 1: Saving a new user
        Date today = new Date();
        Member member = new Member();
        member.setNom("Doe");
        member.setPrenom("John");
        member.setDate_de_naissance(today);

        when(memberRepository.findAll()).thenReturn(List.of(member));
        List<Member> members = memberService.getAllMembers();

        assertEquals(1, members.size());
        assertEquals(member, members.get(0));

    }

    @Test
    void getAllMembers_shouldReturnEmptyListWhenNoMembersExist() {
        when(memberService.getAllMembers()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Map<String, Object>>> response = memberController.getMembers();
        assertNull(response);
    }

    @Test
    public void testCreateMember() {
        // Arrange
        Member member = new Member(); // Create a member object

        // Act
        memberService.createMember(member);

        // Assert
        verify(memberRepository, times(1)).save(member); // Verify that save was called once with the member
    }

    @Test
    public void testGetMemberById() {
        // Arrange
        Long id = 1L;
        Member member = new Member();
        member.setId(id);

        when(memberRepository.findById(id)).thenReturn(java.util.Optional.of(member));

        // Act
        Member result = memberService.getMemberById(id);

        // Assert
        assertEquals(member, result);
    }

    @Test
    public void testGetMemberById_NonExistingMember() {
        when(memberRepository.findById(100L)).thenReturn(java.util.Optional.empty());
        assertThrows(EntityDontExistException.class, () -> memberService.getMemberById(100L));
    }
}
