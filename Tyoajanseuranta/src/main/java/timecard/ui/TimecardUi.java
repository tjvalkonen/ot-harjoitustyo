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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import timecard.dao.FileProjectDao;
import timecard.dao.FileTimecardDao;
import timecard.dao.FileUserDao;
import timecard.domain.TimecardService;
import timecard.domain.Project;
import timecard.domain.Timecard;


public class TimecardUi extends Application {
    private TimecardService timecardService;
    
    
    private Stage primaryStage;
   
    private Scene newUserScene;
    private Scene loginScene;
    
    private Scene projectsListScene;
    private Scene projectScene;
    private Scene addProjectScene;
    
    private Project selectedProject;

    private VBox projectNodes;
    private VBox timecardNodes;
    
    private VBox projectTopPane;
    private VBox projectInfoPane;

    // Style UI elements
    
    private String cssLayoutBorder01 = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 2;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";
        
    private String cssLayoutWhiteBox = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 0;\n" +
                   "-fx-border-width: 0;\n" +
                   "-fx-fill: white;\n";    
   
    private String cssLayoutH2 = "-fx-font-size: 20;\n";       
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));        
        
        String projectFile = properties.getProperty("projectFile");
        String timecardFile = properties.getProperty("timecardFile");
        String userFile = properties.getProperty("userFile");

        FileProjectDao projectDao = new FileProjectDao(projectFile);
        FileTimecardDao timecardDao = new FileTimecardDao(timecardFile);
        FileUserDao userDao = new FileUserDao(userFile);

        timecardService = new TimecardService(projectDao, timecardDao, userDao);
    }

    public Node createProjectNode(Project project) {
        HBox box = new HBox(1);
        box.setStyle(cssLayoutBorder01);
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
        box.setStyle(cssLayoutBorder01);
        Label time  = new Label(Integer.toString(timecard.getTime()));
        time.setPrefWidth(75);
        
        Label type  = new Label();
        
        int typeInt = timecard.getType();
        if (typeInt == 0){
            type.setText("Not selected");
        } else if (typeInt == 1) {
            type.setText("Design");
        } else if (typeInt == 2) {
            type.setText("Programming");
        } else if (typeInt == 3) {
            type.setText("Testing");
        } else if (typeInt == 4) {
            type.setText("Maintenance");
        }
        
        type.setPrefWidth(150);
        Label description  = new Label(timecard.getDescription());
        description.setPrefWidth(250);
        Label username  = new Label(timecard.getUsername());
        username.setPrefWidth(150);
        time.setMinHeight(28);
        type.setMinHeight(28);
        description.setMinHeight(28);        
        username.setMinHeight(28);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,1,0,1));

        box.getChildren().addAll(time, type, description, username);
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
        projectsListButton.setPadding(new Insets(5));
        
        // add timecard 
        
        HBox newTimecardLabelsPane = new HBox(5);
        HBox newTimecardPane = new HBox(5);
        newTimecardPane.setPadding(new Insets(5));
        
        Label newTimecardTimeLabel = new Label("Time");
        newTimecardTimeLabel.setPrefWidth(75);
        TextField newTimecardTimeInput = new TextField();  
        newTimecardTimeInput.setPrefWidth(75);
        
        Label newTimecardTypeLabel = new Label("Type");
        newTimecardTypeLabel.setPrefWidth(150);
        TextField newTimecardTypeInput = new TextField();      
        newTimecardTypeInput.setPrefWidth(150);
        
        ComboBox jobType = new ComboBox();

        jobType.getItems().add("Design");
        jobType.getItems().add("Programming");
        jobType.getItems().add("Testing");
        jobType.getItems().add("Maintenance");
        
        Label newTimecardDescriptionLabel = new Label("Description");
        TextField newTimecardDescriptionInput = new TextField(); 
        newTimecardDescriptionInput.setPrefWidth(150);
        newTimecardPane.setPrefWidth(600);  
        
        Label timecardCreationMessage = new Label();
        
        Button addTimecardButton = new Button("add time");
        addTimecardButton.setPadding(new Insets(5));

        addTimecardButton.setOnAction(e->{
                       
            int time = Integer.parseInt(newTimecardTimeInput.getText());
            int type = 0;
            
            String value = (String) jobType.getValue();
            if (value == null){
               type = 0;
            } else if (value.contentEquals("Design")) {
                type = 1;
            } else if (value.contentEquals("Programming")) {
                type = 2;
            } else if (value.contentEquals("Testing")) {
                type = 3;
            } else if (value.contentEquals("Maintenance")) {
                type = 4;
            }          
            
            String description = newTimecardDescriptionInput.getText();        
            
            if (timecardService.addTimecard(selectedProject.getId(), time, type, description, timecardService.getLoggedUser().getName())){
//              Success!
                redrawProject();
            } else {
//                if something went wrong  
            }
        });
        newTimecardLabelsPane.getChildren().addAll(newTimecardTimeLabel, newTimecardTypeLabel, newTimecardDescriptionLabel);
        newTimecardPane.getChildren().addAll(newTimecardTimeInput, jobType, newTimecardDescriptionInput, addTimecardButton); 

        redrawTimecards();
        
        projectTopPane.getChildren().clear();
        HBox topLabel = new HBox(1);
        topLabel.setPadding(new Insets(0,1,0,1));
        topLabel.getChildren().addAll(projectsListButton, project(selectedProject));
        VBox addTime = new VBox(1);
        addTime.setPadding(new Insets(0,1,0,1));
        addTime.getChildren().addAll(newTimecardLabelsPane, newTimecardPane);
        projectTopPane.getChildren().addAll(topLabel, addTime);  
        
        projectInfoPane.getChildren().clear();
        projectInfoPane.getChildren().addAll();
    }
    
    public Node project (Project project) {
        HBox box = new HBox(1);
        Label label  = new Label(selectedProject.getName());
        label.setMinHeight(1);
        label.setStyle(cssLayoutH2);
        
        Label projectTotalTime = new Label("");
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,1,0,1));
        box.getChildren().addAll(label, projectTotalTime, spacer);
        
        // Timecard
        
        return box;       
    }
    
    public Node timecard (Timecard timecard) {
        HBox box = new HBox(1);
        Label time  = new Label(Integer.toString(timecard.getTime()));
        Label type  = new Label(Integer.toString(timecard.getType()));
        Label description  = new Label(timecard.getDescription());
        Label username  = new Label(timecard.getUsername());
        time.setMinHeight(1);
        type.setMinHeight(1);
        description.setMinHeight(1);
        username.setMinHeight(1);
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,1,0,1));
        box.getChildren().addAll(time, type, description, username, spacer);
        
        return box;     
    }
            
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;        

        // login scene
        
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));
        Label loginLabel = new Label("username");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("login");
        Button createButton = new Button("create new user");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            if (timecardService.login(username) ){
                loginMessage.setText("");
                primaryStage.setScene(projectsListScene);  
                usernameInput.setText("");
            } else {
                loginMessage.setText("No such user!");
                loginMessage.setTextFill(Color.RED);
            }      
        });  
        
        createButton.setOnAction(e->{
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);   
        });  
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);       
        
        loginScene = new Scene(loginPane, 300, 250);
                
        // new createNewUserScene
        
        VBox newUserPane = new VBox(10);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField(); 
        Label newUsernameLabel = new Label("username");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
     
        HBox newNamePane = new HBox(10);
        newNamePane.setPadding(new Insets(10));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("name");
        newNameLabel.setPrefWidth(100);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);        
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = new Button("create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
   
            if ( username.length()==2 || name.length()<2 ) {
                userCreationMessage.setText("username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else if (timecardService.createUser(username, name) ){
                userCreationMessage.setText("");                
                loginMessage.setText("new user created");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(loginScene);      
            } else {
                userCreationMessage.setText("username exists!");
                userCreationMessage.setTextFill(Color.RED);        
            }
 
        });  
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, newNamePane, createNewUserButton); 
       
        newUserScene = new Scene(newUserPane, 300, 250);  

        
        // Projects list scene
        
        HBox projectsTopPane = new HBox(1);
        VBox projectsBottomPane = new VBox(1);

        // List active projects here
        
        ScrollPane projectScollbar = new ScrollPane();
        BorderPane projectsPane = new BorderPane(projectScollbar);

        projectNodes = new VBox(1);
        projectNodes.setMaxWidth(500);
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
        
        projectsListScene = new Scene(projectsPane, 600, 250);

        // Project scene
               
        projectTopPane = new VBox(1);
        projectInfoPane = new VBox(1);
        
        ScrollPane projectTimecardScollbar = new ScrollPane();
        BorderPane projectPane = new BorderPane(projectTimecardScollbar);

        String projectName = "";
        if(selectedProject!= null) { 
            projectName = selectedProject.getName(); 
        }
        
        Label projectLabel  = new Label(projectName); //selectedProject.getName()
        projectLabel.setMinHeight(1);
        
        projectLabel.setStyle(cssLayoutH2);
        
        Button projectsListButton = new Button("Projects List");        
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        // List timecards
        
        //ScrollPane timecardScollbar = new ScrollPane();
        
        timecardNodes = new VBox(1);
        timecardNodes.setMaxWidth(500);
        timecardNodes.setMinWidth(500);
     
        
        projectTimecardScollbar.setContent(timecardNodes);
        
        VBox projectTopPaneTimecards = new VBox(1);
        projectTopPaneTimecards.getChildren().addAll(projectsListButton, projectLabel);
        
        projectTopPane.getChildren().addAll(projectTopPaneTimecards);
        projectPane.setTop(projectTopPane);
        //projectPane.setBottom(projectInfoPane);
        
        projectScene = new Scene(projectPane, 600, 250);
        
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
       
        addProjectScene = new Scene(newProjectPane, 600, 250);               
        
        // setup primary stage
        
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
