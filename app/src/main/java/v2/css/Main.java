package v2.css;

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
        Parent root = FXMLLoader.load(getClass().getResource("/ejemplo_v2.fxml"));
        //El FXML ya tiene CSS asignada desde SB
        Scene scene = new Scene(root);
        
        //Cargamos y aplicacamos nueva CSS desde aquí
        scene.getStylesheets().add(getClass().getResource("/estiloValidac.css").toString());

        primeraEscena.setScene(scene);
        primeraEscena.setTitle("Validaciones CSS");
        primeraEscena.show();  
    }
    
    
}
