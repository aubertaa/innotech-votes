package fr.aaubert.innotechvotesbackend.controller;

import fr.aaubert.innotechvotesbackend.model.Member;
import fr.aaubert.innotechvotesbackend.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMembers_shouldReturnListOfMembers() throws ParseException {
        // Arrange
        Member member1 = new Member(1L, "Doe", "John", new SimpleDateFormat("yyyy-MM-dd").parse("1985-01-12"), false);
        Member member2 = new Member(2L, "Smith", "Jane", new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"), true);
        List<Member> members = List.of(member1, member2);

        when(memberService.getAllMembers()).thenReturn(members);

        // Act
        ResponseEntity<List<Map<String, Object>>> response = memberController.getMembers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        Map<String, Object> memberMap1 = response.getBody().get(0);
        assertEquals(1L, memberMap1.get("id"));
        assertEquals("John", memberMap1.get("prenom"));
        assertEquals("Doe", memberMap1.get("nom"));
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1985-01-12"), memberMap1.get("date_de_naissance"));
        assertEquals(false, memberMap1.get("voteEnregistre"));

        Map<String, Object> memberMap2 = response.getBody().get(1);
        assertEquals(2L, memberMap2.get("id"));
        assertEquals("Jane", memberMap2.get("prenom"));
        assertEquals("Smith", memberMap2.get("nom"));
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"), memberMap2.get("date_de_naissance"));
        assertEquals(true, memberMap2.get("voteEnregistre"));
    }

    @Test
    void createMember_shouldCreateMemberSuccessfully() {
        // Arrange
        Member member = new Member(3L, "Alice", "Brown", null, false);

        // Act
        assertDoesNotThrow(() -> memberController.createMember(member));

        // Assert
        verify(memberService, times(1)).createMember(member);
    }

    @Test
    void createMember_shouldThrowExceptionOnFailure() {
        // Arrange
        Member member = new Member(3L, "Alice", "Brown", null, false);
        doThrow(new RuntimeException("Creation failed")).when(memberService).createMember(member);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberController.createMember(member));
        assertEquals("Creation failed", exception.getReason());
    }

    @Test
    void updateMember_shouldUpdateVoteEnregistreSuccessfully() {
        // Arrange
        Member member = new Member(1L, "John", "Doe", null, false);
        when(memberService.getMemberById(1L)).thenReturn(member);

        // Act
        assertDoesNotThrow(() -> memberController.updateMember(1L, true));

        // Assert
        verify(memberService, times(1)).setVoteEnregistreForMemberId(1L, true);
    }

    @Test
    void updateMember_shouldThrowExceptionIfMemberNotFound() {
        // Arrange
        when(memberService.getMemberById(99L)).thenReturn(null);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberController.updateMember(99L, true));
        assertEquals("Membre inexistant", exception.getReason());
    }

    @Test
    void updateMember_shouldThrowExceptionIfVoteAlreadyRecorded() {
        // Arrange
        Member member = new Member(1L, "John", "Doe", null, true); // voteEnregistre is true
        when(memberService.getMemberById(1L)).thenReturn(member);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberController.updateMember(1L, true));
        assertEquals("Vote déjà enregistré", exception.getReason());
    }
}
