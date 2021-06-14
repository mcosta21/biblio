package com.mcosta.gui;

import com.mcosta.model.User;
import com.mcosta.model.UserTypeEnum;
import com.mcosta.util.AccessProvider;
import com.mcosta.util.AccessProvider.Page;
import com.mcosta.util.ManagerWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends AccessProviderController implements Initializable {
    
    @FXML
    private VBox vboxx;

    private ObservableList<Button> obsListActionButtons = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.getCredentials();
        generateOptionPages();
    }    

    private void generateOptionPages(){

        // AccessProvider.getUser().getUserType()
        List<Page> pages = AccessProvider.getPagesByUserType(UserTypeEnum.ADMIN);

        HBox hboxController = new HBox();
        hboxController.setSpacing(10);
        int controller = 0;

        for(Page page : pages){
            
            Button button = new Button(page.getLabel().toUpperCase());
            button.setOnAction((event) -> {
                try {
                    ManagerWindow.openWindow(page.getUri(), page.getLabel());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ManagerWindow.closeWindow(event);
            });
            
            button.getStyleClass().clear();
            button.getStyleClass().add("main-button");
            controller++;
            
            if(controller == 4) {
                obsListActionButtons.add(button);
                hboxController.getChildren().addAll(obsListActionButtons);
                vboxx.getChildren().add(hboxController);

                for(Button b : obsListActionButtons) {
                     b.prefWidthProperty().bind(vboxx.widthProperty().divide(4));
                }

                hboxController = new HBox();
                hboxController.setSpacing(10);
                obsListActionButtons.clear();
                controller = 0;
            }
            else {
                obsListActionButtons.add(button);

                if(pages.indexOf(page)+1 == pages.size()){
                    hboxController.getChildren().addAll(obsListActionButtons);
                    vboxx.getChildren().add(hboxController);

                    for(Button b : obsListActionButtons) {
                        b.prefWidthProperty().bind(vboxx.widthProperty().divide(4 + 0.10));
                    }
                }
            }
            
        }

    }

}
