package fr.aaubert.innotechvotesbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_entity", uniqueConstraints = @UniqueConstraint(columnNames = {"nom", "prenom", "date_de_naissance"}))
public class Member {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull(message = "Le nom est obligatoire")
    private String nom;
    
    @NotNull(message = "Le prénom est obligatoire")
    private String prenom;

    @NotNull(message = "La date de naissance est obligatoire")
    private Date date_de_naissance;

	@Column(columnDefinition = "boolean default false")
    private Boolean voteEnregistre = false;

    @Override
	public String toString() {
		return "Member [id=" + id + ", Nom=" + nom + ", Prenom=" + prenom + ", Date de naissance=" + date_de_naissance
				+ "]";
	}

}
