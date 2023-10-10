package br.edu.femass.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"nome", "funcao"})
public class Funcionario {
    //Auto numeração (BigSerial) - chave primária
    private Long id;
    //varchar
    private String nome;
    //varchar
    private Funcao funcao;

    @Override
    public String toString() {
        return this.nome;
    }
}
