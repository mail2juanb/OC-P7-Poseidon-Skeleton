package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;


@Slf4j
@Data
@Entity
@Table(name = "users")
public class User implements DomainModel<User> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(updatable = false)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false)
    private String username;

//    mots de passe (au moins une lettre majuscule, au moins 8 caractères, au moins un chiffre et un symbole) ;
    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(nullable = false)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(nullable = false)
    private String role;





    public User update(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();

        return this;
    }

}
