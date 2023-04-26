package br.com.code.plus.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "aluno")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome_completo", length = 200)
    private String name;

    @Column(name = "user_name", length = 200)
    private String userName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "senha", columnDefinition = "TEXT")
    private String password;

    @Column(name = "telefone", length = 15)
    private String phone;

}
