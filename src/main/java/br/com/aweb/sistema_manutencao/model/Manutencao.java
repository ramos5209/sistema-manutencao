package br.com.aweb.sistema_manutencao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer numeroMaq;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @NotBlank
    private String tipoMaq;

    @Column(nullable = false)
    private LocalDateTime criadoEm =  LocalDateTime.now();

    @Column(nullable = true)
    private LocalDate dataFinalizado;    
}
