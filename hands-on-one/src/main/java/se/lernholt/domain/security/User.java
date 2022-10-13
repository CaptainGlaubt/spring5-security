package se.lernholt.domain.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "springuser")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer             id;
    @Column(name = "username", nullable = false)
    private String              username;
    @Column(name = "password", nullable = false)
    private String              password;
    @Column(name = "algorithm", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm encryptionAlgorithm;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "springuser_authority", joinColumns = @JoinColumn(name = "springuser"), inverseJoinColumns = @JoinColumn(name = "authority"))
    List<Authority>             authorities;
}
