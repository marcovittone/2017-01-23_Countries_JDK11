package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<Country> boxNazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	int n;
    	try{
    		n=Integer.parseInt(this.txtAnno.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("errore devi inserire un numero intero");
    		this.txtAnno.clear();
    		return;
    	}
    	
    	if(n<1818 || n> 2006) {
    		this.txtResult.setText("errore devi inserire una data tra il 1818 e 2006 estremi inclusi");
    		this.txtAnno.clear();
    		return;
    	}
    	
    	List<Country>result = this.model.creaGrafo(n);
    	
    	for(Country c: result) {
    		this.txtResult.appendText(c+" "+c.getNumStatiConfinanti()+"\n");
    	}
    	
    	this.boxNazione.getItems().addAll(result);
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	Country c = this.boxNazione.getValue();
    	
    	if(c == null){
    		this.txtResult.setText("SELEZIONARE STATO INSERIMENTO MIGRANTI");
    		return;
    	}
    	
    	this.model.simulate(c);
    	
    	this.txtResult.appendText("NUMERO DI INSTANTI CONSIDERATI :"+this.model.getT()+"\n");
    	this.txtResult.appendText("ELENCO DEI PAESI ACCOGLIENTI: \n");
    	
    	for(Country cc: this.model.getPaesiAccoglienti()) {
    		this.txtResult.appendText(cc+" "+cc.getStanziali()+"\n");
    	}
    	
    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
