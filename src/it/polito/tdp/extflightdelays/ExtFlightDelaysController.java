/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="numeroTratteTxtInput"
    private TextField numeroTratteTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	this.btnAeroportiConnessi.setDisable(false);
		this.btnCercaItinerario.setDisable(false);
    	try {
    		int compagnie=Integer.parseInt(this.compagnieMinimo.getText());
    		model.creaGrafo(compagnie);
    		txtResult.appendText("Grafo Creato\n");
    		this.cmbBoxAeroportoPartenza.getItems().clear();
    		this.cmbBoxAeroportoDestinazione.getItems().clear();
    		this.cmbBoxAeroportoPartenza.getItems().addAll(model.getGrafo().vertexSet());
    		this.cmbBoxAeroportoDestinazione.getItems().addAll(model.getGrafo().vertexSet());
    		if(model.getGrafo().vertexSet().isEmpty()) {
    			txtResult.appendText("Non ci sono aeroporti serviti da almeno "+compagnie+" compagnie\n");
    			this.btnAeroportiConnessi.setDisable(true);
    			this.btnCercaItinerario.setDisable(true);
    		}
    		
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Devi inserire un numero\n");
    	}

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Aeroporti adiacenti a "+cmbBoxAeroportoPartenza.getValue()+":\n");
    	for(Airport a: model.getAeroportiAdiacenti(this.cmbBoxAeroportoPartenza.getValue(), model.getGrafo())) {
    		txtResult.appendText(""+a+"\n");
    	}

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {

    	txtResult.clear();
    	try {
    		int tratte=Integer.parseInt(this.numeroTratteTxtInput.getText());
    		if(model.isConnesso(this.cmbBoxAeroportoPartenza.getValue(),this.cmbBoxAeroportoDestinazione.getValue())) {
    		model.genera(tratte, this.cmbBoxAeroportoPartenza.getValue(), this.cmbBoxAeroportoDestinazione.getValue());
    		txtResult.appendText("Percorso migliore: \n");
    		for(Airport a:model.getSoluzione()) {
    			txtResult.appendText(""+a+"\n");
    		}
    		}
    		else
    			txtResult.appendText("Non esiste percorso");
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Devi inserire un numero\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroTratteTxtInput != null : "fx:id=\"numeroTratteTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    
    public void setModel(Model model) {
  		this.model = model;
  		
  	}
}
