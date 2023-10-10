package br.edu.femass.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.dao.ClienteDao;
import br.edu.femass.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiCliente implements Initializable {

    @FXML
    private TextField TxtCpf;
    @FXML
    private TextField TxtNome;
    @FXML
    private TextField TxtTelefone;
    @FXML
    private TextField TxtEndereco;
    @FXML
    private TextField TxtEmail;
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
    private Button BtnPets;
    @FXML
    private ListView<Cliente> LstClientes;

    private Cliente cliente;
    private ClienteDao clienteDao = new ClienteDao();
    private Boolean incluindo;

    private static Cliente clientePet;

    @FXML
    private void BtnCancelar_Click(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void BtnGravar_Click(ActionEvent event) {
        habilitarEdicao(false);

        if (incluindo) {
            cliente.setCpf(TxtCpf.getText());
        }
        cliente.setEmail(TxtEmail.getText());
        cliente.setEndereco(TxtEndereco.getText());
        cliente.setNome(TxtNome.getText());
        cliente.setTelefone(TxtTelefone.getText());

        try {
            if (incluindo) {
                clienteDao.gravar(cliente);
            } else {
                clienteDao.alterar(cliente);
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
    private void BtnPets_Click(ActionEvent event) {
        clientePet = cliente;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Pet.fxml"));
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage stage = new Stage();

            stage.setTitle("Pets");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void BtnIncluir_Click(ActionEvent event) {
        habilitarEdicao(true);
        incluindo = true;
        cliente = new Cliente();
        TxtCpf.setText("");
        TxtNome.setText("");
        TxtTelefone.setText("");
        TxtEndereco.setText("");
        TxtEmail.setText("");
        TxtCpf.requestFocus();

    }

    @FXML
    private void BtnAlterar_Click(ActionEvent event) {
        habilitarEdicao(true);
        TxtCpf.setDisable(true);
        incluindo = false;


    }

    @FXML
    private void BtnExcluir_Click(ActionEvent event) {
        try {
            clienteDao.excluir(cliente);
            exibirLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void LstClientes_MouseClicked(MouseEvent event) {
        exibirDados();

    }

    @FXML
    private void LstClientes_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exibirLista();
    }

    private void habilitarEdicao(Boolean habilita) {
        TxtCpf.setEditable(habilita);
        TxtEmail.setEditable(habilita);
        TxtEndereco.setEditable(habilita);
        TxtNome.setEditable(habilita);
        TxtTelefone.setEditable(habilita);
        BtnCancelar.setDisable(!habilita);
        BtnGravar.setDisable(!habilita);
        BtnIncluir.setDisable(habilita);
        BtnAlterar.setDisable(habilita);
        BtnExcluir.setDisable(habilita);
        BtnPets.setDisable(habilita);
        LstClientes.setDisable(habilita);

    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION, mensagem);
        alerta.show();
    }

    private void exibirLista() {
        try {
            ObservableList<Cliente> dados = FXCollections.observableArrayList(clienteDao.listar());
            LstClientes.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exibirDados() {

        cliente = LstClientes.getSelectionModel().getSelectedItem();
        if (cliente==null) {
            return;
        }        
        TxtCpf.setText(cliente.getCpf());
        TxtEmail.setText(cliente.getEmail());
        TxtEndereco.setText(cliente.getEndereco());
        TxtNome.setText(cliente.getNome());
        TxtTelefone.setText(cliente.getTelefone());
    }


    public static Cliente getClientePet() {
        return clientePet;
    }

}
