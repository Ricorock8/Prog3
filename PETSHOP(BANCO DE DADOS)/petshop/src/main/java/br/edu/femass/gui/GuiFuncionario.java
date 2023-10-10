package br.edu.femass.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.dao.FuncionarioDao;
import br.edu.femass.model.Funcao;
import br.edu.femass.model.Funcionario;
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

public class GuiFuncionario implements Initializable {

    @FXML
    private TextField TxtNome;

    @FXML
    private ComboBox<Funcao> CboFuncao;

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
    private ListView<Funcionario> LstFuncionarios;

    private Funcionario funcionario;
    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private Boolean incluindo;

    @FXML
    private void BtnCancelar_Click(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void BtnGravar_Click(ActionEvent event) {
        habilitarEdicao(false);

        funcionario.setNome(TxtNome.getText());
        funcionario.setFuncao(CboFuncao.getSelectionModel().getSelectedItem());
        try {
            if (incluindo) {
                funcionarioDao.gravar(funcionario);
            } else {
                funcionarioDao.alterar(funcionario);
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
        funcionario = new Funcionario();
        TxtNome.setText("");
    }

    @FXML
    private void BtnAlterar_Click(ActionEvent event) {
        habilitarEdicao(true);
        incluindo = false;


    }

    @FXML
    private void BtnExcluir_Click(ActionEvent event) {
        try {
            funcionarioDao.excluir(funcionario);
            exibirLista();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void LstFuncionarios_MouseClicked(MouseEvent event) {
        exibirDados();

    }

    @FXML
    private void LstFuncionarios_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exibirLista();
        preencherCombo();
    }

    private void habilitarEdicao(Boolean habilita) {
        TxtNome.setEditable(habilita);
        CboFuncao.setDisable(!habilita);
        BtnCancelar.setDisable(!habilita);
        BtnGravar.setDisable(!habilita);
        BtnIncluir.setDisable(habilita);
        BtnAlterar.setDisable(habilita);
        BtnExcluir.setDisable(habilita);
        LstFuncionarios.setDisable(habilita);

    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION, mensagem);
        alerta.show();
    }

    private void exibirLista() {
        
        try {
            ObservableList<Funcionario> dados = FXCollections.observableArrayList(funcionarioDao.listar());
            LstFuncionarios.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void preencherCombo() {
        try {
            ObservableList<Funcao> dados = FXCollections.observableArrayList(Funcao.values());
            CboFuncao.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exibirDados() {

        funcionario = LstFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario==null) {
            return;
        }        
        TxtNome.setText(funcionario.getNome());
        CboFuncao.getSelectionModel().select(funcionario.getFuncao());

    }


}
