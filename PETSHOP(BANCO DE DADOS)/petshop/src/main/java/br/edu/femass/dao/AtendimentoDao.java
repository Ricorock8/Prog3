package br.edu.femass.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.femass.model.Atendimento;
import br.edu.femass.model.TipoAtendimento;

public class AtendimentoDao extends Persistencia implements Dao<Atendimento>{

    private PetDao petDao = new PetDao();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();

    @Override
    public void gravar(Atendimento objeto) throws Exception {
        String sql = "insert into atendimento (dataatendimento, descricao, tipoatendimento, pet_id, funcionario_id) values (?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setDate(1, Date.valueOf(objeto.getDataAtendimento()));
        ps.setString(2, objeto.getDescricao());
        ps.setString(3, objeto.getTipoAtendimento().toString());
        ps.setLong(4, objeto.getPet().getId());
        ps.setLong(5, objeto.getFuncionario().getId());
        ps.executeUpdate();
        
        
    }

    @Override
    public List<Atendimento> listar() throws Exception {
        String sql = "select * from atendimento order by dataatendimento, pet_id"; 
        PreparedStatement ps = getPreparedStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Atendimento> atendimentos = new ArrayList<Atendimento>();
        
        while (rs.next()) {
            Atendimento at = new Atendimento();
            at.setId(rs.getLong("id"));
            at.setDataAtendimento(rs.getDate("dataatendimento").toLocalDate());
            at.setDescricao(rs.getString("descricao"));
            at.setTipoAtendimento(TipoAtendimento.valueOf(rs.getString("tipoatendimento")));
            at.setPet(petDao.getPorId(rs.getLong("pet_id")));
            at.setFuncionario(funcionarioDao.getPorId(rs.getLong("funcionario_id")));
            atendimentos.add(at);

        }

        return atendimentos;
        
    }

    @Override
    public void alterar(Atendimento objeto) throws Exception {
        String sql = "update atendimento set dataatendimento = ?, descricao = ?, tipoatendimento = ?, pet_id = ?, funcionario_id = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setDate(1, Date.valueOf(objeto.getDataAtendimento()));
        ps.setString(2, objeto.getDescricao());
        ps.setString(3, objeto.getTipoAtendimento().toString());
        ps.setLong(4, objeto.getPet().getId());
        ps.setLong(5, objeto.getFuncionario().getId());       
        ps.setLong(6, objeto.getId()); 

        ps.executeUpdate();
        
    }

    @Override
    public void excluir(Atendimento objeto) throws Exception {
        String sql = "delete from atendimento where id = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1, objeto.getId());
        ps.executeUpdate();
    }

    @Override
    public Atendimento getPorId(Long id) throws Exception {
        String sql = "select * from atendimento where id = ?"; 
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setLong(1,id);
        ResultSet rs = ps.executeQuery();


        if (!(rs.next())) return null;
        Atendimento at = new Atendimento();
        at.setId(rs.getLong("id"));
        at.setDataAtendimento(rs.getDate("dataatendimento").toLocalDate());
        at.setDescricao(rs.getString("descricao"));
        at.setTipoAtendimento(TipoAtendimento.valueOf(rs.getString("tipoatendimento")));
        at.setPet(petDao.getPorId(rs.getLong("pet_id")));
        at.setFuncionario(funcionarioDao.getPorId(rs.getLong("funcionario_id")));

        return at;
    }
    
}
