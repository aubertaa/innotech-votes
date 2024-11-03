package fr.aaubert.innotechvotesbackend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MemberTest {
    @Test
    public void testGetId() {
        Member member = new Member();
        member.setId(1L);
        assertEquals(1L, member.getId());
    }

    @Test
    public void testGetNom() {
        Member member = new Member();
        member.setNom("testuser");
        assertEquals("testuser", member.getNom());
    }

    @Test
    public void testGetPrenom() {
        Member member = new Member();
        member.setPrenom("testuser");
        assertEquals("testuser", member.getPrenom());
    }

    @Test
    public void testGetDateDeNaissance() {
        Member member = new Member();
        Date today = new Date();
        member.setDate_de_naissance(today);
        assertEquals(today, member.getDate_de_naissance());
    }

    @Test
    public void testToString() {
        Member member = new Member();
        member.setId(1L);
        member.setNom("testuser");
        member.setPrenom("testuser");
        member.setDate_de_naissance(new Date());
        assertEquals("Member [id=1, Nom=testuser, Prenom=testuser, Date de naissance=" + member.getDate_de_naissance() + "]", member.toString());
    }


}