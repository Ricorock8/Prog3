package br.edu.femass.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import br.edu.femass.model.Cliente;
import br.edu.femass.model.Pet;
import br.edu.femass.model.TipoPet;

public class PetDao extends Persistencia implements Dao<Pet>{

    ClienteDao clienteDao = new ClienteDao();


    @Override
    public void gravar(Pet objeto) throws Exception {
        validar(objeto);

        String sql = "insert into pet (nome, datanascimento, raca, tipo, cliente_cpf) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1,objeto.getNome());
        ps.setDate(2,Date.valueOf(objeto.getDataNascimento()));
        ps.setString(3,objeto.getRaca());
        ps.setString(4,objeto.getTipo().toString());
        ps.setString(5,objeto.getCliente().getCpf());
        ps.executeUpdate();


    }

    private void validar(Pet objeto) throws Exception{
        if (objeto.getNome()==null) throw new Exception("Nome obrigatório");
        if (objeto.getDataNascimento()==null) throw new Exception("Data de Nascimento obrigatória");
        if (objeto.getRaca()==null) throw new Exception("Raça obrigatório");
        if (objeto.getTipo()==null) throw new Exception("Tipo obrigatório");
        if (objeto.getCliente()==null) throw new Exception("Cliente obrigatório");
    }

    @Override
    public List<Pet> listar() throws Exception{
        String sql = "select * from pet order by pet.nome";
        PreparedStatement ps = getPreparedStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Pet> pets = new ArrayList<Pet>();

        while (rs.next()) {
            Pet p = new Pet();
            p.setCliente(clienteDao.buscarPorCpf(rs.getString("cliente_cpf")));
            p.setId(rs.getLong("id"));
            p.setNome(rs.getString("nome"));
            p.setRaca(rs.getString("raca"));
            p.setTipo(TipoPet.valueOf(rs.getString("tipo")));
            p.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
            pets.add(p);
        }

        return pets;
    }

    public List<Pet> listar(Cliente cliente) throws Exception{
        String sql = "select * from pet where cliente_cpf = ? order by pet.nome";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, cliente.getCpf());
        ResultSet rs = ps.executeQuery();

        List<Pet> pets = new ArrayList<Pet>();

        while (rs.next()) {
            Pet p = new Pet();
            p.setCliente(cliente);
            p.setId(rs.getLong("id"));
            p.setNome(rs.getString("nome"));
            p.setRaca(rs.getString("raca"));
            p.setTipo(TipoPet.valueOf(rs.getString("tipo")));
            p.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
            pets.add(p);
        }

        return pets;
    }

    @Override
    public void alterar(Pet objeto) throws Exception {
        String sql = "update pet set nome = ?, raca = ?, tipo = ?, datanascimento = ?, cliente_cpf = ? where id = ? ";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, objeto.getNome());
        ps.setString(2, objeto.getRaca());
        ps.setString(3, objeto.getTipo().toString());
        ps.setDate(4,Date.valueOf(objeto.getDataNascimento()));
        ps.setString(5, objeto.getCliente().getCpf());
        ps.setLong(6, objeto.getId());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Pet objeto) throws Exception {
        String sql = "delete from pet where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1, objeto.getId());
        ps.executeUpdate();
        
    }

    @Override
    public Pet getPorId(Long id) throws Exception{
        String sql = "select * from pet where id= ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (!(rs.next())) return null;

        Pet p = new Pet();
        p.setCliente(clienteDao.buscarPorCpf(rs.getString("cliente_cpf")));
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setRaca(rs.getString("raca"));
        p.setTipo(TipoPet.valueOf(rs.getString("tipo")));
        p.setDataNascimento(rs.getDate("datanascimento").toLocalDate());

        return p;        
    }
    
}
