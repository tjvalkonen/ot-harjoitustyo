package timecard.ui;

import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import timecard.dao.FileProjectDao;
import timecard.domain.TimecardService;
import timecard.domain.Project;

public class TimecardUi extends Application {
    private TimecardService timecardService;
    
    private Scene projectsScene;
    private Scene addProjectScene;
        
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));        
        
        String projectFile = properties.getProperty("projectFile");

        FileProjectDao projectDao = new FileProjectDao(projectFile);

        timecardService = new TimecardService(projectDao);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        // new project scene
        VBox projectsPane = new VBox(10);
        HBox projectsListPane = new HBox(10);
        projectsPane.setPadding(new Insets(10));
        Label projectsLabel = new Label("Projects:");
        
        // List active projects here
        
        projectsListPane.getChildren().addAll(projectsLabel);
        
        Button addProjectsButton = new Button("add new project");
        
        addProjectsButton.setOnAction(e->{
            primaryStage.setScene(addProjectScene);   
        });         
        
        projectsPane.getChildren().addAll(projectsListPane, addProjectsButton);       
        
        projectsScene = new Scene(projectsPane, 400, 250);

        // add ProjectScene
        
        VBox newProjectPane = new VBox(10);
        
        HBox newProjectNamePane = new HBox(10);
        newProjectNamePane.setPadding(new Insets(10));
        TextField newProjectNameInput = new TextField(); 
        Label newProjectNameLabel = new Label("Project Name");
        newProjectNameLabel.setPrefWidth(150);
        newProjectNamePane.getChildren().addAll(newProjectNameLabel, newProjectNameInput);      
        
        Label projectCreationMessage = new Label();
        
        Button addProjectButton = new Button("add project");
        addProjectButton.setPadding(new Insets(10));

        addProjectButton.setOnAction(e->{
            String name = newProjectNameInput.getText();
            if (timecardService.AddProject(name)){
//                Success!
                primaryStage.setScene(projectsScene);      
            } else {
//                if something went wrong  
            }
 
        });  
        
        newProjectPane.getChildren().addAll(projectCreationMessage, newProjectNamePane, addProjectButton); 
       
        addProjectScene = new Scene(newProjectPane, 400, 250);               
        
        // setup primary stage
        
        primaryStage.setScene(projectsScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
