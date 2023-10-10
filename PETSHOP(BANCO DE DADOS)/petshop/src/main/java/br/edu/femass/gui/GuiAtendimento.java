package br.edu.femass.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.edu.femass.dao.AtendimentoDao;
import br.edu.femass.dao.FuncionarioDao;
import br.edu.femass.dao.PetDao;
import br.edu.femass.model.Atendimento;
import br.edu.femass.model.Funcionario;
import br.edu.femass.model.Pet;
import br.edu.femass.model.TipoAtendimento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GuiAtendimento implements Initializable {

    @FXML
    private TextField TxtDescricao;
    @FXML
    private TextField TxtData;

    @FXML
    private ComboBox<TipoAtendimento> CboTipo;
    @FXML
    private ComboBox<Pet> CboPet;
    @FXML
    private ComboBox<Funcionario> CboFuncionario;

    @FXML
    private Button BtnCancelar;
    @FXML
    private Button BtnGravar;
    @FXML
    private Button BtnIncluir;
    @FXML
    private Button BtnAlterar;
    @FXML
    private Button BtnExcluir;
    @FXML
    private ListView<Atendimento> LstAtendimentos;

    private Atendimento atendimento;
    private AtendimentoDao atendimentoDao = new AtendimentoDao();
    private Boolean incluindo;

    @FXML
    private void BtnCancelar_Click(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void BtnGravar_Click(ActionEvent event) {
        habilitarEdicao(false);

        atendimento.setDescricao(TxtDescricao.getText());
        atendimento.setDataAtendimento(LocalDate.parse(TxtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        atendimento.setFuncionario(CboFuncionario.getSelectionModel().getSelectedItem());
        atendimento.setPet(CboPet.getSelectionModel().getSelectedItem());
        atendimento.setTipoAtendimento(CboTipo.getSelectionModel().getSelectedItem());
        try {
            if (incluindo) {
                atendimentoDao.gravar(atendimento);
            } else {
                atendimentoDao.alterar(atendimento);
            }
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
        } finally {
            exibirMensagem("Dados gravados com sucesso");
            exibirLista();
            exibirDados();
        }
    }

    @FXML
    private void BtnIncluir_Click(ActionEvent event) {
        habilitarEdicao(true);
        incluindo = true;
        atendimento = new Atendimento();
        TxtDescricao.setText("");
        TxtData.setText("");
    }

    @FXML
    private void BtnAlterar_Click(ActionEvent event) {
        habilitarEdicao(true);
        incluindo = false;


    }

    @FXML
    private void BtnExcluir_Click(ActionEvent event) {
        try {
            atendimentoDao.excluir(atendimento);
            exibirLista();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void LstAtendimentos_MouseClicked(MouseEvent event) {
        exibirDados();

    }

    @FXML
    private void LstAtendimentos_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exibirLista();
        preencherCombo();
    }

    private void habilitarEdicao(Boolean habilita) {
        TxtDescricao.setEditable(habilita);
        TxtData.setEditable(habilita);
        CboFuncionario.setDisable(!habilita);
        CboPet.setDisable(!habilita);
        CboTipo.setDisable(!habilita);
        BtnCancelar.setDisable(!habilita);
        BtnGravar.setDisable(!habilita);
        BtnIncluir.setDisable(habilita);
        BtnAlterar.setDisable(habilita);
        BtnExcluir.setDisable(habilita);
        LstAtendimentos.setDisable(habilita);

    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION, mensagem);
        alerta.show();
    }

    private void exibirLista() {
        
        try {
            ObservableList<Atendimento> dados = FXCollections.observableArrayList(atendimentoDao.listar());
            LstAtendimentos.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void preencherCombo() {
        try {
            ObservableList<TipoAtendimento> dados = FXCollections.observableArrayList(TipoAtendimento.values());
            CboTipo.setItems(dados);

            ObservableList<Funcionario> dadosfunc = FXCollections.observableArrayList(new FuncionarioDao().listar());
            CboFuncionario.setItems(dadosfunc);

            ObservableList<Pet> dadosPet = FXCollections.observableArrayList(new PetDao().listar());
            CboPet.setItems(dadosPet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exibirDados() {

        atendimento = LstAtendimentos.getSelectionModel().getSelectedItem();
        if (atendimento==null) {
            return;
        }        
        TxtDescricao.setText(atendimento.getDescricao());
        TxtData.setText(atendimento.getDataAtendimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        CboTipo.getSelectionModel().select(atendimento.getTipoAtendimento());
        CboFuncionario.getSelectionModel().select(atendimento.getFuncionario());
        CboPet.getSelectionModel().select(atendimento.getPet());

    }


}
