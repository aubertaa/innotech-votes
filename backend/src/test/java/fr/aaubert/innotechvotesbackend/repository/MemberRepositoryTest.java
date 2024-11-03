package fr.aaubert.innotechvotesbackend.repository;

import fr.aaubert.innotechvotesbackend.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        // Initialize a sample member and save it to the in-memory database
        Member member = new Member();
        member.setPrenom("Johnny");
        member.setNom("Doe");
        member.setVoteEnregistre(false); // Initial voteEnregistre is false
        member.setDate_de_naissance(new Date());
        member.setId(27L);
        savedMember = memberRepository.save(member);
    }

    @Test
    void setVoteEnregistreForMemberId_shouldNotThrowExceptionForNonExistentMemberId() {
        // Arrange
        Long nonExistentId = 999L;

        // Act & Assert
        assertDoesNotThrow(() -> memberRepository.setVoteEnregistreForMemberId(nonExistentId, true));

        // Ensure no changes happened to the database for existing records
        Optional<Member> existingMember = memberRepository.findById(savedMember.getId());
        assertTrue(existingMember.isPresent());
        assertEquals(false, existingMember.get().getVoteEnregistre());
    }

}
