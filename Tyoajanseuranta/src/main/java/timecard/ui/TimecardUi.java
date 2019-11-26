package timecard.ui;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    
    
    private Stage primaryStage;
    
    private Scene projectsListScene;
    private Scene projectScene;
    private Scene addProjectScene;
    
    private Project selectedProject;

    private VBox projectNodes;
    
    
    private HBox projectTopPane;
    private VBox projectInfoPane;
    
    
        
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));        
        
        String projectFile = properties.getProperty("projectFile");

        FileProjectDao projectDao = new FileProjectDao(projectFile);

        timecardService = new TimecardService(projectDao);
    }

    public Node createProjectNode(Project project) {
        HBox box = new HBox(10);
        Label label  = new Label(project.getName());
        label.setMinHeight(28);

        Button button = new Button("Select");
        button.setOnAction(e->{
            // Project timecard
            //project(project);
            
            this.selectedProject = project;
            primaryStage.setScene(projectScene);
            redrawProject();

        });
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label, spacer, button);
        return box;
    }
    
    public void redrawProjectlist() {
        projectNodes.getChildren().clear();     

        List<Project> projects = timecardService.getProjects();
        projects.forEach(project->{
            projectNodes.getChildren().add(createProjectNode(project));
        });     
    }
    
    
    public void redrawProject() {
        Button projectsListButton = new Button("Projects List");        
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        projectTopPane.getChildren().clear();
        projectTopPane.getChildren().addAll(projectsListButton);
  
        projectInfoPane.getChildren().clear();
        projectInfoPane.getChildren().add(project(selectedProject));
    }
    
    public Node project (Project project) {
        HBox box = new HBox(10);
        Label label  = new Label(selectedProject.getName());
        label.setMinHeight(28);
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,5,0,5));
        box.getChildren().addAll(label, spacer);
        
        // Timecard
        
        return box;
        
    }
            
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        
        // Projects list scene
        
        HBox projectsTopPane = new HBox(10);
        VBox projectsBottomPane = new VBox(10);

        // List active projects here
        
        ScrollPane projectScollbar = new ScrollPane();
        BorderPane projectsPane = new BorderPane(projectScollbar);

        projectNodes = new VBox(10);
        projectNodes.setMaxWidth(280);
        projectNodes.setMinWidth(280);
        redrawProjectlist();
        
        projectScollbar.setContent(projectNodes);
        projectsPane.setTop(projectsTopPane);
        projectsPane.setBottom(projectsBottomPane);

        Button addProjectsButton = new Button("Add Project");     
        addProjectsButton.setOnAction(e->{
            primaryStage.setScene(addProjectScene);   
        });      
    
        projectsTopPane.getChildren().addAll(addProjectsButton);       
        projectsBottomPane.getChildren().addAll();
        
        projectsListScene = new Scene(projectsPane, 400, 250);
        
        
        
        
        // Project scene
               
        projectTopPane = new HBox(10);
        projectInfoPane = new VBox(10);
        //HBox projectBox = new HBox(10);
        
        ScrollPane projectTimecardScollbar = new ScrollPane();
        BorderPane projectPane = new BorderPane(projectTimecardScollbar);

        String projectName = "";
        if(selectedProject!= null) { 
            projectName = selectedProject.getName(); 
        }
        
        Label projectLabel  = new Label(projectName); //selectedProject.getName()
        projectLabel.setMinHeight(28);
        
        Button projectsListButton = new Button("Projects List");        
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });    
        
        projectTopPane.getChildren().addAll(projectsListButton);
        projectPane.setTop(projectTopPane);
        projectPane.setCenter(projectInfoPane);
        
        projectScene = new Scene(projectPane, 400, 250);
        
        
        
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
            if (timecardService.addProject(name)){
//                Success!
                primaryStage.setScene(projectsListScene);
                redrawProjectlist();
            } else {
//                if something went wrong  
            }
 
        });  
        
        newProjectPane.getChildren().addAll(projectsListButton, projectCreationMessage, newProjectNamePane, addProjectButton); 
       
        addProjectScene = new Scene(newProjectPane, 400, 250);               
        
        // setup primary stage
        
        primaryStage.setScene(projectsListScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
