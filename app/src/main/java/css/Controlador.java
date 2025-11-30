package css;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Molina
 */
public class Controlador implements Initializable {

    private List<String> stylesheets;
    private int indexCSS = 0;
    ArrayList<String> nombres = new ArrayList<>(Arrays.asList("clasico", "moderno", "minimalista", "oscuro", "empresarial", "colorido", "retro", "futurista", "elegante"));

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField edadTextField;

    @FXML
    private RadioButton damRadioButton;

    @FXML
    private RadioButton dawRadioButton;

    @FXML
    private CheckBox condicCheckBox;

    @FXML
    private ComboBox<String> provinciaComboBox;

    @FXML
    private Label etiqCiclo;

    @FXML
    private Label etiqEdad;

    @FXML
    private Label etiqEmail;

    @FXML
    private Label etiqNombre;

    @FXML
    private Label etiqProv;

    @FXML
    private Label etiqIconoNombre;

    @FXML
    private Button boton;

    @FXML
    private Label titulo;

    @FXML
    void comprobarDatos(ActionEvent event) {
        //Comprobación de campos
    }

    @FXML
    private VBox raiz; //Raiz para aplicar keyEvent

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup ciclo = new ToggleGroup();
        damRadioButton.setToggleGroup(ciclo);
        dawRadioButton.setToggleGroup(ciclo);

        ObservableList<String> provinciasAndalucia = FXCollections.observableArrayList("Almería", "Cádiz", "Córdoba", "Granada", "Huelva", "Jaén", "Málaga", "Sevilla");
        // Agregar las provincias al ComboBox
        provinciaComboBox.getItems().addAll(provinciasAndalucia);
        
        //APLICAR CSS POR CÓDIGO
        //Se aplica directamente por estilo al primer radio
        damRadioButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        //Se aplica por ID y por clase
        titulo.setId("titulo-personalizado");
        condicCheckBox.getStyleClass().add("check-especial");
        //condicCheckBox.getStyleClass().clear(); //Limpiaría el estilo asignado

        stylesheets = new ArrayList<>();
        //Cargamos todas las hojas de estilo en el array
        for (int i = 0; i <= 8; i++) {
            stylesheets.add(getClass().getResource("/styles/estilo" + i + ".css").toString());
        }

        //Aplicamos evento a toda la jerarquía, al pulsar F1 cambia la CSS
        raiz.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.F1) {
                raiz.getScene().getStylesheets().remove(stylesheets.get(indexCSS)); //Se borra la CSS
                //System.out.println(indexCSS);
                Stage stage = (Stage) raiz.getScene().getWindow();
                stage.setTitle("CSS: "+nombres.get(indexCSS));//Cambiamos título
                raiz.getScene().getStylesheets().add(stylesheets.get(indexCSS));//Aplicamos CSS al nodo raíz
                indexCSS = (indexCSS + 1) % stylesheets.size();//Subimos índice
            }
        });

    }

}
