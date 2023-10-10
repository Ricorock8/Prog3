package br.edu.femass.model;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"dataAtendimento", "descricao", "tipoAtendimento", "pet", "funcionario"})
public class Atendimento {
    //Auto numeração - bigserial - Chave primária
    private Long id;
    //date
    private LocalDate dataAtendimento;
    //varchar
    private String descricao;
    //varchar
    private TipoAtendimento tipoAtendimento;
    //Long (chave estrangeira - pet_id)
    private Pet pet;
    //Long (chave estrangeira - funcionario_id)
    private Funcionario funcionario;
    
    @Override
    public String toString() {
    	return this.descricao;
    }

}
