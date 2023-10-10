package br.edu.femass.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
    }


    @FXML
    private void BtnCliente_Click(ActionEvent event) {
        abrirTela("Cliente");
        
    }    

    @FXML
    private void BtnFuncionario_Click(ActionEvent event) {
        abrirTela("Funcionario");
    } 

    @FXML
    private void BtnAtendimento_Click(ActionEvent event) {
        abrirTela("Atendimento");
    }   
    
    

    private void abrirTela(String tela) {
        try {
            String caminho = "/fxml/" + tela + ".fxml";
            Parent root = FXMLLoader.load(getClass().getResource(caminho));
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage stage = new Stage();

            stage.setTitle(tela);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
}
