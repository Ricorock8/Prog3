package br.edu.femass.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.femass.model.Funcao;
import br.edu.femass.model.Funcionario;

public class FuncionarioDao extends Persistencia implements Dao<Funcionario>{

    @Override
    public void gravar(Funcionario objeto) throws Exception {
        String sql = "Insert into funcionario (nome, funcao) values (?, ?)";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setString(1, objeto.getNome());
        ps.setString(2, objeto.getFuncao().toString());
        ps.executeUpdate();

    }

    @Override
    public List<Funcionario> listar() throws Exception {
        String sql = "select * from funcionario order by nome";
        PreparedStatement ps = getPreparedStatement(sql);

        ResultSet rs = ps.executeQuery();

        List<Funcionario> result = new ArrayList<Funcionario>();
        while (rs.next()) {
            Funcionario f = new Funcionario();
            f.setId(rs.getLong("id"));
            f.setNome(rs.getString("nome"));
            f.setFuncao(Funcao.valueOf(rs.getString("funcao")));
            result.add(f);
        }

        return result;
    }

    @Override
    public void alterar(Funcionario objeto) throws Exception {
        String sql = "update funcionario set nome = ?, funcao = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);

        ps.setString(1, objeto.getNome());
        ps.setString(2, objeto.getFuncao().toString());
        ps.setLong(3, objeto.getId());

        ps.executeUpdate();

    }

    @Override
    public void excluir(Funcionario objeto) throws Exception {
        String sql = "delete from funcionario where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1, objeto.getId());

        ps.executeUpdate();
    }

    public Funcionario getPorId(Long id) throws Exception {
        String sql = "select * from funcionario where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();

        if (!(rs.next())) return null;

        Funcionario f = new Funcionario();
        f.setId(rs.getLong("id"));
        f.setNome(rs.getString("nome"));
        f.setFuncao(Funcao.valueOf(rs.getString("funcao")));
        return f;
    }

    
}
