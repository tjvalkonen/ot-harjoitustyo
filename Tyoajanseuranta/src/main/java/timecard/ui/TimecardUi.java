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
    // private VBox projectInfoPane;

    // Style UI elements
    
    private String cssLayoutBorder01 = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 2;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";

    private String cssLayoutTestBorder = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 0;\n" +
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
        box.setPadding(new Insets(0,0,0,0));
        
        box.getChildren().addAll(label, spacer, button);
        return box;
    }
    
    public Node createTimecardNode(Timecard timecard) {
        HBox box = new HBox(0);
        //box.setStyle(cssLayoutBorder01);
        
        int t = timecard.getTime();
        int hours = t / 60;
        int minutes = t % 60;
        String timeSting = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";

        Label time  = new Label(timeSting);
        time.setPrefWidth(125);        
        time.setStyle(cssLayoutTestBorder);
        
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
        
        type.setPrefWidth(152);
        type.setStyle(cssLayoutTestBorder);
        
        Label description  = new Label(timecard.getDescription());
        description.setPrefWidth(255);
        description.setStyle(cssLayoutTestBorder);
        
        
        Label username  = new Label(timecard.getUsername());
        username.setPrefWidth(150);
        username.setStyle(cssLayoutTestBorder);
        
        time.setMinHeight(28);
        type.setMinHeight(28);
        description.setMinHeight(28);        
        username.setMinHeight(28);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,0,0,0));

        box.getChildren().addAll(time, type, description, username);
        box.setMaxWidth(700);
        box.setMinWidth(700);
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
        
        //HBox newTimecardLabelsPane = new HBox(5);
        HBox newTimecardPane = new HBox(0);
        newTimecardPane.setPadding(new Insets(0));
        
        Label newTimecardHoursLabel = new Label("Hours");
        newTimecardHoursLabel.setPrefWidth(60);
        Label newTimecardMinutesLabel = new Label("Minutes");
        newTimecardMinutesLabel.setPrefWidth(60);
        TextField newTimecardHoursInput = new TextField();  
        newTimecardHoursInput.setPrefWidth(60);
        TextField newTimecardMinutesInput = new TextField();  
        newTimecardMinutesInput.setPrefWidth(60);
        
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
        newTimecardDescriptionInput.setPrefWidth(250);
        newTimecardPane.setPrefWidth(750);  
        
        //Label timecardCreationMessage = new Label();
        
        Label addTimecardButtonLabel = new Label(""); 
        
        Button addTimecardButton = new Button("add time");
        addTimecardButton.setPadding(new Insets(5));

        addTimecardButton.setOnAction(e->{
                       
            int time = Integer.parseInt(newTimecardHoursInput.getText())*60+Integer.parseInt(newTimecardMinutesInput.getText());
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
        
        VBox timeInputH = new VBox();
        timeInputH.setStyle(cssLayoutTestBorder);
        timeInputH.getChildren().addAll(newTimecardHoursLabel, newTimecardHoursInput);
        
        VBox timeInputM = new VBox();
        timeInputM.setStyle(cssLayoutTestBorder);
        timeInputM.getChildren().addAll(newTimecardMinutesLabel, newTimecardMinutesInput);
        
        VBox typeInput = new VBox();
        typeInput.setStyle(cssLayoutTestBorder);
        typeInput.getChildren().addAll(newTimecardTypeLabel, jobType);
        
        VBox descriptionInput = new VBox();
        descriptionInput.setStyle(cssLayoutTestBorder);
        descriptionInput.getChildren().addAll(newTimecardDescriptionLabel, newTimecardDescriptionInput);

        VBox addTimecardButtonBox = new VBox();
        addTimecardButtonBox.setStyle(cssLayoutTestBorder);
        addTimecardButtonBox.getChildren().addAll(addTimecardButtonLabel, addTimecardButton);
        
        // add new timecard form
        // newTimecardLabelsPaddTimecardButtonBoxane.getChildren().addAll(newTimecardHoursLabel,newTimecardMinutesLabel, newTimecardTypeLabel, newTimecardDescriptionLabel);
        // newTimecardPane.getChildren().addAll(newTimecardHoursInput, newTimecardMinutesInput, jobType, newTimecardDescriptionInput, addTimecardButton); 
        newTimecardPane.getChildren().addAll(timeInputH, timeInputM, typeInput, descriptionInput, addTimecardButtonBox); 
        
        newTimecardPane.setMaxWidth(700);
        newTimecardPane.setMinWidth(700);
        
        redrawTimecards();
        
        projectTopPane.getChildren().clear();
        HBox topLabel = new HBox(1);
        topLabel.setPadding(new Insets(0,0,0,0));
        topLabel.getChildren().addAll(projectsListButton, project(selectedProject));

        VBox addTime = new VBox(1);
        addTime.setPadding(new Insets(0,0,0,0));
        addTime.getChildren().addAll(newTimecardPane);
        addTime.setStyle(cssLayoutTestBorder);

        projectTopPane.getChildren().addAll(topLabel, addTime);  
        
//        projectInfoPane.getChildren().clear();
//        projectInfoPane.getChildren().addAll();
    }
    
    public Node project (Project project) {
        HBox box = new HBox(1);
        Label projectName  = new Label(selectedProject.getName());
        projectName.setMinHeight(1);
        projectName.setStyle(cssLayoutH2);

        int t = timecardService.getProjectTotalTime(selectedProject.getId());
        int hours = t / 60;
        int minutes = t % 60;
        String timeSting = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";        
        
        Label projectTotalTime = new Label(timeSting);

        int etc = selectedProject.getEtc();
        int etcHours = etc / 60;
        int etcMinutes = etc % 60;
        String etcSting = Integer.toString(etcHours) + "h " + Integer.toString(etcMinutes) + "min";
        
        Label projectEtc = new Label(etcSting);
        
        Region spacer = new Region();
        
        box.setPadding(new Insets(0,0,0,0));
        box.getChildren().addAll(projectName, projectTotalTime, projectEtc, spacer);
        
        // Timecard
        
        return box;       
    }
//    
//    public Node timecard (Timecard timecard) {
//        HBox box = new HBox(1);
//        Label time  = new Label(Integer.toString(timecard.getTime()));
//        
//        Label type  = new Label(Integer.toString(timecard.getType()));
//        Label description  = new Label(timecard.getDescription());
//        Label username  = new Label(timecard.getUsername());
//        time.setMinHeight(1);
//        type.setMinHeight(1);
//        description.setMinHeight(1);
//        username.setMinHeight(1);
//        Region spacer = new Region();
//        
//        box.setPadding(new Insets(0,1,0,1));
//        box.getChildren().addAll(time, type, description, username, spacer);
//        
//        return box;     
//    }
//            
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

        projectNodes = new VBox(0);
        projectNodes.setMaxWidth(500);
        projectNodes.setMinWidth(500);
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
        
        projectsListScene = new Scene(projectsPane, 750, 250);

        // Project scene
               
        projectTopPane = new VBox(0);
//        projectInfoPane = new VBox(0);
        
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
        
        timecardNodes = new VBox(0);
        timecardNodes.setMaxWidth(700);
        timecardNodes.setMinWidth(700);
     
        
        projectTimecardScollbar.setContent(timecardNodes);
        
        VBox projectTopPaneTimecards = new VBox(1);
        projectTopPaneTimecards.getChildren().addAll(projectsListButton, projectLabel);
        
        projectTopPane.getChildren().addAll(projectTopPaneTimecards);
        projectPane.setTop(projectTopPane);
        //projectPane.setBottom(projectInfoPane);
        
        projectScene = new Scene(projectPane, 750, 250);
        
        // add ProjectScene
        
        VBox newProjectPane = new VBox(1);
        
        HBox newProjectNamePane = new HBox(0);
        newProjectNamePane.setPadding(new Insets(0));
        TextField newProjectNameInput = new TextField(); 
        Label newProjectNameLabel = new Label("Project Name");
        newProjectNameLabel.setPrefWidth(150);

        VBox nameInput = new VBox();
        nameInput.setStyle(cssLayoutTestBorder);
        nameInput.getChildren().addAll(newProjectNameLabel, newProjectNameInput);

        Label newEtcHoursLabel = new Label("Hours");
        newEtcHoursLabel.setPrefWidth(60);
        Label newEtcMinutesLabel = new Label("Minutes");
        newEtcMinutesLabel.setPrefWidth(60);
        TextField newEtcHoursInput = new TextField();  
        newEtcHoursInput.setPrefWidth(60);
        TextField newEtcMinutesInput = new TextField();  
        newEtcMinutesInput.setPrefWidth(60);
        
        VBox etcInputH = new VBox();
        etcInputH.setStyle(cssLayoutTestBorder);
        etcInputH.getChildren().addAll(newEtcHoursLabel, newEtcHoursInput);
        
        VBox etcInputM = new VBox();
        etcInputM.setStyle(cssLayoutTestBorder);
        etcInputM.getChildren().addAll(newEtcMinutesLabel, newEtcMinutesInput);

        // Label projectCreationMessage = new Label();
        
        newProjectNamePane.getChildren().addAll(nameInput, etcInputH, etcInputM);
        
        Button addProjectButton = new Button("add project");
        addProjectButton.setPadding(new Insets(5));

        addProjectButton.setOnAction(e->{
            String name = newProjectNameInput.getText();
            int etc = Integer.parseInt(newEtcHoursInput.getText())*60+Integer.parseInt(newEtcMinutesInput.getText());
            if (timecardService.addProject(name, etc)){
//                Success!
                primaryStage.setScene(projectsListScene);
                redrawProjectlist();
            } else {
//                if something went wrong  
            }
 
        });  
        
        newProjectPane.getChildren().addAll(projectsListButton, newProjectNamePane, addProjectButton); 
       
        addProjectScene = new Scene(newProjectPane, 600, 250);               
        
        // setup primary stage
        
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
