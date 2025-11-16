package css;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        //El FXML ya tiene CSS asignada desde SB
        Scene scene = new Scene(root);
        
        //Cargamos la nueva CSS desde aquí (aplica clases directamente, subclases e ID lógicamente no)
        scene.getStylesheets().add(getClass().getResource("/estilosNetBeans.css").toString());
        //scene.getStylesheets().remove(0);//Elimina la primera css añadida desde aquí
        //scene.getStylesheets().clear();//Las limpia todas

        primeraEscena.setScene(scene);
        primeraEscena.setTitle("CSS (Pulsa F1!!)");
        primeraEscena.show();

    }

}
