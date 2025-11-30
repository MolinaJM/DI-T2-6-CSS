package v2.css;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

/**
 *
 * @author Molina
 */
public class Controlador implements Initializable {

    List<ValidationSupport> validadores;//Creamos una lista para contener validadores

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup ciclo = new ToggleGroup();
        damRadioButton.setToggleGroup(ciclo);
        dawRadioButton.setToggleGroup(ciclo);
        damRadioButton.setSelected(true);

        ObservableList<String> provinciasAndalucia = FXCollections.observableArrayList("", "Almería", "Cádiz", "Córdoba", "Granada", "Huelva", "Jaén", "Málaga", "Sevilla");
        provinciaComboBox.getItems().addAll(provinciasAndalucia);//Agregamos las provincias al ComboBox
        provinciaComboBox.getSelectionModel().selectFirst();

        //Importante: se llevan los ToolTip a las etiquetas porque los controles se controlarán por medio del Validator
        etiqNombre.setTooltip(new Tooltip("Nombre de usuario. El nombre debe tener entre 5 y 10 caracteres"));
        etiqEmail.setTooltip(new Tooltip("Formato <texto>@<texto>.<texto>"));
        etiqEdad.setTooltip(new Tooltip("Número (máximo 3 dígitos). Restricción entre 0 y 99"));

        //Cargamos los gráficos
        ImageView iconoOk = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("img/ok_icon.png")));
        ImageView iconoErr = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("img/error_icon.png")));
        iconoOk.setFitHeight(16);
        iconoOk.setFitWidth(16);
        iconoErr.setFitHeight(16);
        iconoErr.setFitWidth(16);

        //VALIDACIONES----------------------------------------------------------------
        //NO es necesario usar un validador por cada CONTROL, solo se hace por didáctica, 
        //para que NO muestre todos los errores de todos los campos de golpe y se pueda entender mejor
        ValidationSupport vSnombre = new ValidationSupport();

        //Podemos registrar todos los validadores que queramos pero OJO! Se pueden pisar y por tanto solo mostrar uno de ellos!
        //CONFIGURAMOS COMPORTAMIENTO: Puedo indicar si quiero que la comprobación esté activa o no 
        //vSnombre.setErrorDecorationEnabled(false);//AVISO SIEMPRE ACTIVO (opción por defecto)
        //A) CREAR VALIDADOR ANTES DE REGISTRARLO (me podría interesaar aplicarlo a varios controles):
        //Predicado: condición/es para dar validador por superado que devuelve true o false en forma de texto+icono
//        Validator<String> validadorCompuesto = Validator.createPredicateValidator(
//                texto -> (texto.length() > 5 && texto.length() < 10), //Lo que ha de cumplir, si no, crea TOOLTIP
//                "Val 1: El nombre debe tener entre 5 y 10 caracteres", //
//                Severity.ERROR //Icono parte inferior izquierda
//        );
//        vSnombre.registerValidator(nombreTextField, validadorCompuesto);
        //Se puede añadir otro validador, pero pisaría gráficamente al primero.
        //Descomentamos B y probamos.
        //B) VALIDADOR CUSTOM DEVOLVIENDO EL VALIDATIONRESULT CONDICIONAL:
        //Método alternativo: devuelve el tipo de severity fromTIPOIf (fromWarningIf fromInfoIf fromErrorIf) 
        //si cumple la condición. Hay que indicar en la cabecera el control y la variable a instanciar con el campo
//        vSnombre.registerValidator(nombreTextField, (Control c, String texto) -> {
//            return ValidationResult.fromInfoIf(c, "Val 2: El nombre TIENE entre 5 y 10 caracteres " + c.getId(),
//                    (texto.length() >= 5 && texto.length() <= 10));
//        });
        //Si descomentamos C, el tooltip gráfico de C los pisa a A y B
        //C) VALIDADOR CUSTOM COMPLETO CON VARIAS SALIDAS
        vSnombre.registerValidator(nombreTextField, (Control c, String texto) -> {
            if (texto.length() == 0) {
                return ValidationResult.fromWarning(c, "Val 3: El nombre no debe estar vacío");
            } else if (texto.length() < 5 || texto.length() > 10) {
                return ValidationResult.fromError(c, "Val 3: El nombre debe tener entre 5 y 10 caracteres");
            } else {
                return ValidationResult.fromInfo(c, "Val 3: OK");
            }
        });

        //D) OTRAS DECORACIONES: ICONOS. El método setGraphic se puede aplicar a los hijos de Labeled: Label, Button
        // Checkbox, RadioButton y ToggleButton. 
        GraphicValidationDecoration decorador = new GraphicValidationDecoration() {
            @Override
            public void applyValidationDecoration(ValidationMessage message) {
                super.applyValidationDecoration(message);
                System.out.println("Mensaje:" + message);
                if (message.getSeverity() == Severity.ERROR || message.getSeverity() == Severity.WARNING) {
                    etiqNombre.setGraphic(iconoPersonalizadoEtiqueta());//ACTIVAMOS 
                    etiqNombre.setContentDisplay(ContentDisplay.LEFT);//Se coloca a izquierda
                    etiqIconoNombre.setGraphic(iconoErr);//ICONO ERROR
                } else if (message.getSeverity() == Severity.INFO) {
                    etiqNombre.setGraphic(null);//Desactiva ICONO
                    etiqIconoNombre.setGraphic(iconoOk);//ICONO OK
                }
            }
        };

        //Se registran el resto de validadores
        ValidationSupport vSmail = new ValidationSupport();
        vSmail.registerValidator(emailTextField, Validator.createRegexValidator("El formato del email es inválido", "^(.+)@(.+)\\.(.+)$", Severity.ERROR));

        //Validador para Edad
        ValidationSupport vSedad = new ValidationSupport();
        Validator<String> rangoValidator = Validator.createPredicateValidator(valor -> {
            if (valor == null || valor.isEmpty()) {
                //Estamos haciendo una especie de EmptyValidator
                return false;
            }
            try {
                int number = Integer.parseInt(valor);
                return number >= 0 && number <= 99;//false si está fuera de rango, entonces muestra cadena
            } catch (NumberFormatException e) {
                return false; //Maneja el caso donde valor no es un número válido
            }
        }, "Valor debe estar entre 0 y 99 o NO es un número");
        //Registramos el validador
        vSedad.registerValidator(edadTextField, true, rangoValidator);

        ValidationSupport vSprov = new ValidationSupport();
        //Validador para Provincia
        //Podríamos usar un Empty pero no genera ningún aviso gráfico:
        //vSprov.registerValidator(provinciaComboBox, Validator.createEmptyValidator("Elige Provincia!!") );
        //En su lugar usamos un Predicado
        Validator<String> validadorCombo = Validator.createPredicateValidator(
                texto -> !("".equals(texto)),
                "Hay que elegir provincia!", //
                Severity.ERROR //Icono parte inferior izquierda
        );
        vSprov.registerValidator(provinciaComboBox, validadorCombo);

        //Validador Custom para CheckBox
        ValidationSupport vScheck = new ValidationSupport();
        vScheck.registerValidator(condicCheckBox, true, (Control c, Boolean v) -> {
            if (!v) {
                return ValidationResult.fromError(c, "Acepta las condiciones!!");
            } else {
                return ValidationResult.fromInfo(c, "Condiciones aceptadas!");
            }
        });

        //No tiene mucho sentido hacer un Validador para los radiobotones ya que de forma normal uno
        //siempre está marcado.
        //Registro de Validadores para la comprobación
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSnombre, vSmail, vSedad, vSprov, vScheck));

        //Iniciamos la decoración en todos los controles en un nuevo hilo,
        //esto es necesario porque 
        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
            //Asociamos el decorador a la caja de nombre
            vSnombre.setValidationDecorator(decorador);
        });

        //Validación CSS------------------------------------------------------------------------------------------
        //Aplicamos estilos CSS en función del resultado de la validación
       vSnombre.validationResultProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue.getErrors().isEmpty() && newValue.getWarnings().isEmpty()) {
               nombreTextField.getStyleClass().remove("error");
           } else {
               if (!nombreTextField.getStyleClass().contains("error")) {
                   nombreTextField.getStyleClass().add("error");//evita volver a aplicar estilo
               }
           }
       });

        //Variante: Recorremos todos los validadores y aplicamos css
        //Requiere crear el efecto DropShadow        
    //     for (ValidationSupport vS : validadores) {
    //         vS.validationResultProperty().addListener((observable, oldValue, newValue) -> {
    //             Set<Control> controles = vS.getRegisteredControls();//Cogemos control/es en un Set (por definición no tiene duplicados y todo es del mismo tipo)
    //             System.out.println(controles.size());
    //             for (Control c : controles) {//Recorremos Set
    //                 System.out.println(c);
    //                 if (newValue.getErrors().isEmpty() && newValue.getWarnings().isEmpty()) {
    //                     c.getStyleClass().remove("error");
    //                     // c.setEffect(creaDropShadow(Color.GREEN)); //Descomentar una vez visto el efecto de .error
    //                 } else {
    //                     if (!c.getStyleClass().contains("error")) {
    //                         c.getStyleClass().add("error");//evita volver a aplicar estilo
    //                         // c.setEffect(creaDropShadow(Color.RED)); //Descomentar una vez visto el efecto de .error
    //                     }
    //                 }
    //             }
    //         });
    //     }
    }
    
    private DropShadow creaDropShadow(Color c){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10); // Radio de la sombra
        dropShadow.setOffsetX(5); // Desplazamiento en X
        dropShadow.setOffsetY(5); // Desplazamiento en Y
        dropShadow.setColor(c);
        return dropShadow;
    }

    //Validación CSS------------------------------------------------------------------------------------------

    private Label iconoPersonalizadoEtiqueta() {
        Label errorLabel = new Label("X");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        return errorLabel;
    }

    @FXML
    public void comprobarDatos() {
        //Lanzamos los validadores
        //Se sacan todos los errores encontrados y se puede realizar tareas según esto
        System.out.println("Comprobación de DATOS");
        System.out.println("=====================");
        for (ValidationSupport validationSupport : validadores) {
            ValidationResult resultados = validationSupport.getValidationResult();
            System.out.println("Validador: " + validationSupport.getRegisteredControls());
            System.out.println("Errores: " + resultados.getErrors());
            System.out.println("Infos: " + resultados.getInfos());
            System.out.println("Mensajes: " + resultados.getMessages());
            System.out.println("Warnings: " + resultados.getWarnings());
        }

        //También se pueden comprobar los validadores de forma individual 
        //(habría que poner vSnombre como variable de clase):
        //System.out.println("Validador Nombre " + vSnombre.getValidationResult().getErrors());
        //Comprobamos si todo OK
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        if (todoOK) {
            alert.setContentText("TODO CORRECTO!!!");
        } else {
            alert.setContentText("El formulario sigue teniendo ERRORES!!!");
        }
        alert.showAndWait();

    }

}
