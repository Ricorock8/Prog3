package br.edu.femass.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.femass.model.Cliente;

public class ClienteDao extends Persistencia implements Dao<Cliente>{

    @Override
    public void gravar(Cliente objeto) throws Exception {
        String sql = "insert into cliente (cpf, nome, endereco, telefone, email) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1,objeto.getCpf());
        ps.setString(2,objeto.getNome());
        ps.setString(3,objeto.getEndereco());
        ps.setString(4,objeto.getTelefone());
        ps.setString(5,objeto.getEmail());
        ps.executeUpdate();

    }

    @Override
    public List<Cliente> listar() throws Exception{
        String sql = "select * from cliente order by nome";
        PreparedStatement ps = getPreparedStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Cliente> clientes = new ArrayList<Cliente>();

        while (rs.next()) {
            Cliente c = new Cliente();
            c.setCpf(rs.getString("cpf"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEmail(rs.getString("email"));
            c.setEndereco(rs.getString("endereco"));

            clientes.add(c);
        }

        return clientes;
    }

    public Cliente buscarPorCpf(String cpf) throws Exception{
        String sql = "select * from cliente where cpf = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();

        if (rs.first() && rs.last()) {
            return null;
        }

        rs.next();
        Cliente c = new Cliente();
        c.setCpf(rs.getString("cpf"));
        c.setNome(rs.getString("nome"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        c.setEndereco(rs.getString("endereco"));

        return c;
    }

    @Override
    public void alterar(Cliente objeto) throws Exception {
        String sql = "update cliente set nome = ?, endereco = ?, telefone = ?, email = ? where cpf = ? ";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, objeto.getNome());
        ps.setString(2, objeto.getEndereco());
        ps.setString(3, objeto.getTelefone());
        ps.setString(4, objeto.getEmail());
        ps.setString(5, objeto.getCpf());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Cliente objeto) throws Exception {
        String sql = "delete from cliente where cpf = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, objeto.getCpf());
        ps.executeUpdate();
        
    }

    @Override
    public Cliente getPorId(Long id) throws Exception {
        throw new Exception("Busque por CPF");
    }
    
}
