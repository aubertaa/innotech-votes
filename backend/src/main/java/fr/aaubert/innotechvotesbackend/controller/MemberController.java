package fr.aaubert.innotechvotesbackend.controller;

import fr.aaubert.innotechvotesbackend.model.Member;
import fr.aaubert.innotechvotesbackend.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getMembers() {
        List<Member> members = memberService.getAllMembers();

        // Build response map with memberId included
        List<Map<String, Object>> response = members.stream()
                .sorted(Comparator.comparing(Member::getNom)
                        .thenComparing(Member::getPrenom))
                .map(member -> {
                    Map<String, Object> memberMap = new HashMap<>();
                    memberMap.put("id", member.getId());
                    memberMap.put("prenom", member.getPrenom());
                    memberMap.put("nom", member.getNom());
                    memberMap.put("date_de_naissance", member.getDate_de_naissance());
                    memberMap.put("voteEnregistre", member.getVoteEnregistre());
                    return memberMap;
                }).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/member")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createMember(@RequestBody Member member) {
        try {
            memberService.createMember(member);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creation failed");
        }
    }

    @PostMapping("/member/enregistrer-vote")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateMember(@RequestParam("id") Long id, @RequestParam("voteEnregistre") Boolean voteEnregistre) {

        Member member = memberService.getMemberById(id);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Membre inexistant");
        } else if (member.getVoteEnregistre()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vote déjà enregistré");
        }

        memberService.setVoteEnregistreForMemberId(id, voteEnregistre);

    }

}
