package br.edu.femass.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"nome", "tipo", "dataNascimento", "cliente", "raca"})
public class Pet {

    private Long id;
    private String nome;
    private TipoPet tipo;
    private LocalDate dataNascimento;
    private String raca;
    private Cliente cliente;

    @Override
    public String toString() {
        return this.nome;
    }

    public Long getIdade() {
        return ChronoUnit.YEARS.between(LocalDate.now(), this.dataNascimento);
        
    }


    
}
