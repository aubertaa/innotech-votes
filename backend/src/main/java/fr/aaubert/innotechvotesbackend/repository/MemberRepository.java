package fr.aaubert.innotechvotesbackend.repository;

import fr.aaubert.innotechvotesbackend.model.Member;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional
    @Modifying
    @Query("""
             UPDATE Member
             SET voteEnregistre = :voteEnregistre
             WHERE id = :id
        """)
    void setVoteEnregistreForMemberId(Long id, Boolean voteEnregistre);

}
