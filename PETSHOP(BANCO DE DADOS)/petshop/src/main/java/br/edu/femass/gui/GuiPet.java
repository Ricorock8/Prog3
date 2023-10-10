package br.edu.femass.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.edu.femass.dao.PetDao;
import br.edu.femass.model.Cliente;
import br.edu.femass.model.Pet;
import br.edu.femass.model.TipoPet;
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

public class GuiPet implements Initializable {

    @FXML
    private TextField TxtCliente;
    @FXML
    private TextField TxtNome;
    @FXML
    private TextField TxtDataNascimento;
    @FXML
    private TextField TxtRaca;

    @FXML
    private ComboBox<TipoPet> CboTipo;

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
    private ListView<Pet> LstPets;

    private Cliente cliente;
    private Pet pet;
    private PetDao petDao = new PetDao();
    private Boolean incluindo;

    @FXML
    private void BtnCancelar_Click(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void BtnGravar_Click(ActionEvent event) {
        habilitarEdicao(false);

        pet.setCliente(cliente);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        pet.setDataNascimento(LocalDate.parse(TxtDataNascimento.getText(), df));
        pet.setNome(TxtNome.getText());
        pet.setRaca(TxtRaca.getText());
        pet.setTipo(CboTipo.getSelectionModel().getSelectedItem());
        try {
            if (incluindo) {
                petDao.gravar(pet);
            } else {
                petDao.alterar(pet);
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
        pet = new Pet();
        TxtNome.setText("");
        TxtDataNascimento.setText("");
        TxtRaca.setText("");
    }

    @FXML
    private void BtnAlterar_Click(ActionEvent event) {
        habilitarEdicao(true);
        incluindo = false;


    }

    @FXML
    private void BtnExcluir_Click(ActionEvent event) {
        try {
            petDao.excluir(pet);
            exibirLista();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void LstPets_MouseClicked(MouseEvent event) {
        exibirDados();

    }

    @FXML
    private void LstPets_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cliente = GuiCliente.getClientePet();
        TxtCliente.setText(this.cliente.getNome());
        exibirLista();
        preencherCombo();
    }

    private void habilitarEdicao(Boolean habilita) {
        TxtNome.setEditable(habilita);
        TxtDataNascimento.setEditable(habilita);
        TxtRaca.setEditable(habilita);
        CboTipo.setDisable(!habilita);
        BtnCancelar.setDisable(!habilita);
        BtnGravar.setDisable(!habilita);
        BtnIncluir.setDisable(habilita);
        BtnAlterar.setDisable(habilita);
        BtnExcluir.setDisable(habilita);
        LstPets.setDisable(habilita);

    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION, mensagem);
        alerta.show();
    }

    private void exibirLista() {
        if (cliente==null) return;
        try {
            ObservableList<Pet> dados = FXCollections.observableArrayList(petDao.listar(cliente));
            LstPets.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void preencherCombo() {
        try {
            ObservableList<TipoPet> dados = FXCollections.observableArrayList(TipoPet.values());
            CboTipo.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exibirDados() {

        pet = LstPets.getSelectionModel().getSelectedItem();
        if (pet==null) {
            return;
        }        
        TxtNome.setText(pet.getNome());
        TxtDataNascimento.setText(pet.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        TxtRaca.setText(pet.getRaca());
        CboTipo.getSelectionModel().select(pet.getTipo());

        TxtCliente.setText(cliente.getNome());
    }


}
