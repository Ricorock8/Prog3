package br.edu.femass.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"nome", "endereco", "telefone", "email"})
public class Cliente {
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    @Override
    public String toString() {
        return this.nome;
    }
    
}
