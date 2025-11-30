package css;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Molina
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primeraEscena) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ejemplo_css.fxml"));
        // El FXML ya tiene CSS asignada desde SB
        Scene scene = new Scene(root);

        //Uso de Fuentes TTF
        //A) Podemos usar una fuente existente en el SSOO: -fx-font-family: "Pixar";
        for (String fuentesLocales : Font.getFamilies())
            System.out.println(fuentesLocales);// Listamos fuentes locales

        //B) Registrar la fuente en el runtime de JAVAFX
        Font miFuente = Font.loadFont(getClass().getResourceAsStream("/fonts/Pixar.ttf"), 12);
        if (miFuente == null) {
            System.out.println("Error! Fuente no cargada");
        } else {
                System.out.println("Cargada: "+miFuente.getName());
        }

        // Cargamos la nueva CSS desde aquí (aplica clases directamente, subclases e ID)
        scene.getStylesheets().add(getClass().getResource("/estilosPorCódigo.css").toString());
        // scene.getStylesheets().remove(0);//Elimina la primera css añadida desde aquí (quita estilo de título, radios y checkbos)
        // scene.getStylesheets().clear();//Las limpia todas las aplicadas desde aquí por código (si hubiera más de una)

        primeraEscena.setScene(scene);
        primeraEscena.setTitle("CSS (Pulsa F1!!)");
        primeraEscena.show();

    }

}
