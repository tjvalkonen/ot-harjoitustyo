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
import timecard.dao.FileTimecardDao;
import timecard.domain.TimecardService;
import timecard.domain.Project;
import timecard.domain.Timecard;

public class TimecardUi extends Application {
    private TimecardService timecardService;
    
    
    private Stage primaryStage;
    
    private Scene projectsListScene;
    private Scene projectScene;
    private Scene addProjectScene;
    
    private Project selectedProject;

    private VBox projectNodes;
    private VBox timecardNodes;
    
    private VBox projectTopPane;
    private VBox projectInfoPane;

    // Style UI elements
    
    private String cssLayout = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 2;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";
        
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));        
        
        String projectFile = properties.getProperty("projectFile");
        String timecardFile = properties.getProperty("timecardFile");

        FileProjectDao projectDao = new FileProjectDao(projectFile);
        FileTimecardDao timecardDao = new FileTimecardDao(timecardFile);

        timecardService = new TimecardService(projectDao, timecardDao);
    }

    public Node createProjectNode(Project project) {
        HBox box = new HBox(1);
        box.setStyle(cssLayout);
        Label label  = new Label(project.getName());
        label.setMinHeight(1);

        Button button = new Button("Select");
        button.setOnAction(e->{
            this.selectedProject = project;
            primaryStage.setScene(projectScene);
            redrawProject();

        });
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,1,0,1));
        
        box.getChildren().addAll(label, spacer, button);
        return box;
    }
    
    public Node createTimecardNode(Timecard timecard) {
        HBox box = new HBox(5);
        box.setStyle(cssLayout);
        Label time  = new Label(Integer.toString(timecard.getTime()));
        time.setPrefWidth(75);
        Label type  = new Label(Integer.toString(timecard.getType()));
        type.setPrefWidth(75);
        Label description  = new Label(timecard.getDescription());
        description.setPrefWidth(250);
        time.setMinHeight(28);
        type.setMinHeight(28);
        description.setMinHeight(28);        
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,1,0,1));

        box.getChildren().addAll(time, type, description ); // spacer
        return box;
    }
    
    public void redrawTimecards() {
        timecardNodes.getChildren().clear();
        
        List<Timecard> timecards = timecardService.getTimecards(selectedProject.getId());
        timecards.forEach(timecard->{
            timecardNodes.getChildren().add(createTimecardNode(timecard));
        });
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
        
        // add timecard 
        
        HBox newTimecardLabelsPane = new HBox(5);
        HBox newTimecardPane = new HBox(5);
        newTimecardPane.setPadding(new Insets(5));
        
        Label newTimecardTimeLabel = new Label("Time");
        newTimecardTimeLabel.setPrefWidth(75);
        TextField newTimecardTimeInput = new TextField();  
        newTimecardTimeInput.setPrefWidth(75);
        
        Label newTimecardTypeLabel = new Label("Type");
        newTimecardTypeLabel.setPrefWidth(75);
        TextField newTimecardTypeInput = new TextField();      
        newTimecardTypeInput.setPrefWidth(75);
        
        Label newTimecardDescriptionLabel = new Label("Description");
        TextField newTimecardDescriptionInput = new TextField(); 
        newTimecardDescriptionInput.setPrefWidth(250);
        newTimecardPane.setPrefWidth(450);  
        
        Label timecardCreationMessage = new Label();
        
        Button addTimecardButton = new Button("add time");
        addTimecardButton.setPadding(new Insets(5));

        addTimecardButton.setOnAction(e->{
                       
            int time = Integer.parseInt(newTimecardTimeInput.getText());
            int type = Integer.parseInt(newTimecardTypeInput.getText());
            String description = newTimecardDescriptionInput.getText();        
            
            if (timecardService.addTimecard(selectedProject.getId(), time, type, description)){
//                Success!
                redrawProject();
            } else {
//                if something went wrong  
            }
        });
        newTimecardLabelsPane.getChildren().addAll(newTimecardTimeLabel, newTimecardTypeLabel, newTimecardDescriptionLabel);
        newTimecardPane.getChildren().addAll(newTimecardTimeInput, newTimecardTypeInput, newTimecardDescriptionInput, addTimecardButton); 

        redrawTimecards();
        
        projectTopPane.getChildren().clear();
        projectTopPane.getChildren().addAll(projectsListButton,project(selectedProject), newTimecardLabelsPane, newTimecardPane);  
        
        projectInfoPane.getChildren().clear();
        projectInfoPane.getChildren().add(project(selectedProject));
    }
    
    public Node project (Project project) {
        HBox box = new HBox(1);
        Label label  = new Label(selectedProject.getName());
        label.setMinHeight(1);
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,1,0,1));
        box.getChildren().addAll(label, spacer);
        
        // Timecard
        
        return box;       
    }
    
    public Node timecard (Timecard timecard) {
        HBox box = new HBox(1);
        Label time  = new Label(Integer.toString(timecard.getTime()));
        Label type  = new Label(Integer.toString(timecard.getType()));
        Label description  = new Label(timecard.getDescription());
        time.setMinHeight(1);
        type.setMinHeight(1);
        description.setMinHeight(1);
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,1,0,1));
        box.getChildren().addAll(time, type, description, spacer);
        
        return box;     
    }
            
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;        
        
        // Projects list scene
        
        HBox projectsTopPane = new HBox(1);
        VBox projectsBottomPane = new VBox(1);

        // List active projects here
        
        ScrollPane projectScollbar = new ScrollPane();
        BorderPane projectsPane = new BorderPane(projectScollbar);

        projectNodes = new VBox(1);
        projectNodes.setMaxWidth(400);
        projectNodes.setMinWidth(400);
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
        
        projectsListScene = new Scene(projectsPane, 500, 250);
        

        
        // Project scene
               
        projectTopPane = new VBox(1);
        projectInfoPane = new VBox(1);
        //HBox projectBox = new HBox(10);
        
        ScrollPane projectTimecardScollbar = new ScrollPane();
        BorderPane projectPane = new BorderPane(projectTimecardScollbar);

        String projectName = "";
        if(selectedProject!= null) { 
            projectName = selectedProject.getName(); 
        }
        
        Label projectLabel  = new Label(projectName); //selectedProject.getName()
        projectLabel.setMinHeight(1);
        
        Button projectsListButton = new Button("Projects List");        
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        // List timecards
        
        //ScrollPane timecardScollbar = new ScrollPane();
        
        timecardNodes = new VBox(1);
        timecardNodes.setMaxWidth(400);
        timecardNodes.setMinWidth(400);
     
        
        projectTimecardScollbar.setContent(timecardNodes);       
        
        projectTopPane.getChildren().addAll(projectsListButton);
        projectPane.setTop(projectTopPane);
        //projectPane.setBottom(projectInfoPane);
        
        projectScene = new Scene(projectPane, 500, 250);
        
        
        
        // add ProjectScene
        
        VBox newProjectPane = new VBox(1);
        
        HBox newProjectNamePane = new HBox(1);
        newProjectNamePane.setPadding(new Insets(1));
        TextField newProjectNameInput = new TextField(); 
        Label newProjectNameLabel = new Label("Project Name");
        newProjectNameLabel.setPrefWidth(150);
        newProjectNamePane.getChildren().addAll(newProjectNameLabel, newProjectNameInput);      
        
        Label projectCreationMessage = new Label();
        
        Button addProjectButton = new Button("add project");
        addProjectButton.setPadding(new Insets(5));

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
       
        addProjectScene = new Scene(newProjectPane, 500, 250);               
        
        // setup primary stage
        
        primaryStage.setScene(projectsListScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
