package se.lernholt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth_otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneTimePassword {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "code")
    private String code;
}
