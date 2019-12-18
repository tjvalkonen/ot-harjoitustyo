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
    private Scene projectSummaryScene;
    private Scene addProjectScene;
    
    private Project selectedProject;

    private VBox projectNodes;
    private VBox timecardNodes;
    
    private VBox projectTopPane;
    private VBox projectSummaryPane;
    // private VBox projectInfoPane;

    // Style UI elements
    
    
    private String cssLayoutTopNavigation = "-fx-border-color: gray;\n" +
                   "-fx-background-color: deepskyblue;\n" +
                   "-fx-border-insets: 0;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";
    
    private String cssLayoutBorder01 = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 2;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";

    private String cssLayoutTestBorder = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 0;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n";

    private String cssLayoutBackgroundLightGray = "-fx-background-color: #DDDDDD;\n";
    
    private String cssLayoutWhiteBox = "-fx-border-color: gray;\n" +
                   "-fx-border-insets: 0;\n" +
                   "-fx-border-width: 0;\n" +
                   "-fx-fill: white;\n";    
   
    private String cssLayoutH2 = "-fx-font-size: 16;\n";       
    
    private String cssLayoutBold = "-fx-font-weight: bold;\n";   
    
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
        HBox box = new HBox(0);
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

        Button projectsListButton = new Button("Projects");        
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        projectsListButton.setPadding(new Insets(5));
        
        // add timecard 
        
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

        //Label timecardCreationMessage = new Label();
        
        Label addTimecardButtonLabel = new Label(""); 
        
        Button addTimecardButton = new Button("Add Time");
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
        newTimecardPane.getChildren().addAll(timeInputH, timeInputM, typeInput, descriptionInput, addTimecardButtonBox); 
        
        newTimecardPane.setMaxWidth(700);
        newTimecardPane.setMinWidth(700);
        newTimecardPane.setStyle(cssLayoutBackgroundLightGray);
        
        redrawTimecards();
        
        Button projectSummaryButton = new Button("Summary");  
        projectSummaryButton.setPadding(new Insets(5));
        projectSummaryButton.setOnAction(e->{
            primaryStage.setScene(projectSummaryScene);
            redrawProjectSummary();
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        }); 
        
        projectTopPane.getChildren().clear();
//        HBox topNavigation = new HBox();
//        topNavigation.setPadding(new Insets(10));
//        topNavigation.getChildren().addAll(projectsListButton);
             
        HBox topLabel = new HBox(0);
        topLabel.setPadding(new Insets(0));      
        topLabel.setStyle(cssLayoutTopNavigation);
        topLabel.getChildren().addAll(projectsListButton, projectSummaryButton, logoutButton, project(selectedProject));

        VBox addTime = new VBox();
        addTime.setPadding(new Insets(0,0,0,0));
        addTime.getChildren().addAll(newTimecardPane);
        addTime.setStyle(cssLayoutTestBorder);

        projectTopPane.getChildren().addAll(topLabel, addTime);
    }
    
    public void redrawProjectSummary() {
        
        projectSummaryPane.getChildren().clear();
        
        Button buttonTimecards = new Button("Timecards");
        buttonTimecards.setPadding(new Insets(5));
        buttonTimecards.setOnAction(e->{
            //this.selectedProject = project;
            primaryStage.setScene(projectScene);
            redrawProject();
        });
        
        Button projectsListButton02 = new Button("Projects");  
        projectsListButton02.setPadding(new Insets(5));
        projectsListButton02.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        }); 
        
//        HBox summaryTopLabel = new HBox(0);
//        summaryTopLabel.setPadding(new Insets(0,0,0,0));
//        summaryTopLabel.getChildren().addAll(projectsListButton02, buttonTimecards, project(selectedProject)); // project(selectedProject)

        HBox topLabel = new HBox(0);
        topLabel.setPadding(new Insets(0));
        topLabel.getChildren().addAll(projectsListButton02, buttonTimecards, logoutButton, project(selectedProject));
        
        // job types and total times
        
        VBox summaryPane = new VBox();
        summaryPane.setPadding(new Insets(10,10,10,10));

        Label notselectedTime  = new Label(timecardService.getProjectJobTypeTime(selectedProject.getId(), 0));
        notselectedTime.setMinHeight(0);
        notselectedTime.setPrefWidth(100);
        notselectedTime.setPadding(new Insets(0));

        Label notselectedTimeTitle  = new Label("Not Selected:");
        notselectedTimeTitle.setMinHeight(0);
        notselectedTimeTitle.setPrefWidth(100);
        notselectedTimeTitle.setPadding(new Insets(0));
        
        HBox notselectedTimeRow = new HBox();
        notselectedTimeRow.setMinHeight(0);
        notselectedTimeRow.setPrefWidth(100);
        notselectedTimeRow.getChildren().addAll(notselectedTimeTitle, notselectedTime);
        
        Label designTime  = new Label(timecardService.getProjectJobTypeTime(selectedProject.getId(), 1));
        designTime.setMinHeight(0);
        designTime.setPrefWidth(100);
        designTime.setPadding(new Insets(0));
        
        Label designTimeTitle  = new Label("Desing:");
        designTimeTitle.setMinHeight(0);
        designTimeTitle.setPrefWidth(100);
        designTimeTitle.setPadding(new Insets(0));
        
        HBox designTimeRow = new HBox();
        designTimeRow.setMinHeight(0);
        designTimeRow.setPrefWidth(100);
        designTimeRow.getChildren().addAll(designTimeTitle, designTime);
        
        Label programmingTime  = new Label(timecardService.getProjectJobTypeTime(selectedProject.getId(), 2));
        programmingTime.setMinHeight(0);
        programmingTime.setPrefWidth(100);
        programmingTime.setPadding(new Insets(0));
        
        Label programmingTimeTitle  = new Label("Programming:");
        programmingTimeTitle.setMinHeight(0);
        programmingTimeTitle.setPrefWidth(100);
        programmingTimeTitle.setPadding(new Insets(0));
        
        HBox programmingTimeRow = new HBox();
        programmingTimeRow.setMinHeight(0);
        programmingTimeRow.setPrefWidth(100);
        programmingTimeRow.getChildren().addAll(programmingTimeTitle, programmingTime);
            
        Label testingTime  = new Label(timecardService.getProjectJobTypeTime(selectedProject.getId(), 3));
        testingTime.setMinHeight(0);
        testingTime.setPrefWidth(100);
        testingTime.setPadding(new Insets(0));
        
        Label testingTimeTitle  = new Label("Testing:");
        testingTimeTitle.setMinHeight(0);
        testingTimeTitle.setPrefWidth(100);
        testingTimeTitle.setPadding(new Insets(0));
        
        HBox testingTimeRow = new HBox();
        testingTimeRow.setMinHeight(0);
        testingTimeRow.setPrefWidth(100);
        testingTimeRow.getChildren().addAll(testingTimeTitle, testingTime);
           
        Label maintenanceTime  = new Label(timecardService.getProjectJobTypeTime(selectedProject.getId(), 4));
        maintenanceTime.setMinHeight(0);
        maintenanceTime.setPrefWidth(100);
        maintenanceTime.setPadding(new Insets(0));
        
        Label maintenanceTimeTitle  = new Label("Maintenance:");
        maintenanceTimeTitle.setMinHeight(0);
        maintenanceTimeTitle.setPrefWidth(100);
        maintenanceTimeTitle.setPadding(new Insets(0));
        
        HBox maintenanceTimeRow = new HBox();
        maintenanceTimeRow.setMinHeight(0);
        maintenanceTimeRow.setPrefWidth(100);
        maintenanceTimeRow.getChildren().addAll(maintenanceTimeTitle, maintenanceTime);

        Label totalTime  = new Label(timecardService.getProjectTotalTime(selectedProject.getId()));
        totalTime.setStyle(cssLayoutBold);
        totalTime.setMinHeight(0);
        totalTime.setPrefWidth(100);
        totalTime.setPadding(new Insets(0));
        
        Label totalTimeTitle  = new Label("Total Time:");
        totalTimeTitle.setStyle(cssLayoutBold);
        totalTimeTitle.setMinHeight(0);
        totalTimeTitle.setPrefWidth(100);
        totalTimeTitle.setPadding(new Insets(0));
        
        HBox totalTimeRow = new HBox();
        totalTimeRow.setMinHeight(0);
        totalTimeRow.setPrefWidth(100);
        totalTimeRow.getChildren().addAll(totalTimeTitle, totalTime);
        
        summaryPane.getChildren().addAll(notselectedTimeRow, designTimeRow, programmingTimeRow, testingTimeRow, maintenanceTimeRow, totalTimeRow);
        
        projectSummaryPane.getChildren().addAll(topLabel, summaryPane);
    }
    
    public Node project (Project project) {
        HBox box = new HBox();
        Label projectName  = new Label(selectedProject.getName());
        projectName.setMinHeight(1);
        projectName.setStyle(cssLayoutH2);
        projectName.setPrefWidth(300);
        projectName.setPadding(new Insets(5,5,5,5));

//        int t = timecardService.getProjectTotalTime(selectedProject.getId());
//        int hours = t / 60;
//        int minutes = t % 60;
//        String timeSting = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";        
        
//        Label projectTotalTime = new Label(timeSting);
//
//        Label projectTotalTimeTitle = new Label("Total time");
//        VBox projectTotalTimeBox = new VBox();       
//        projectTotalTimeBox.setStyle(cssLayoutTestBorder);
//        projectTotalTimeBox.getChildren().addAll(projectTotalTimeTitle ,projectTotalTime);
//        projectTotalTimeBox.setPrefWidth(120);

//        int etc = selectedProject.getEtc();
//        int etcHours = etc / 60;
//        int etcMinutes = etc % 60;
//        String etcSting = Integer.toString(etcHours) + "h " + Integer.toString(etcMinutes) + "min";       
//        Label projectEtc = new Label(etcSting);
//        
//        Label projectEtcTitle = new Label("ETC");
//        VBox projectEtcBox = new VBox();       
//        projectEtcBox.setStyle(cssLayoutTestBorder);
//        projectEtcBox.getChildren().addAll(projectEtcTitle ,projectEtc);
//        projectEtcBox.setPrefWidth(120);

        box.setPadding(new Insets(0,0,0,0));
        box.getChildren().addAll(projectName); // projectTotalTimeBox, projectEtcBox
        
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
        
        Button createNewUserButton = new Button("Create");
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
        
        HBox projectsTopPane = new HBox(0);
        //VBox projectsBottomPane = new VBox(0);

        // List active projects here
        
        ScrollPane projectScollbar = new ScrollPane();
        BorderPane projectsPane = new BorderPane(projectScollbar);

        projectNodes = new VBox(0);
        projectNodes.setMaxWidth(500);
        projectNodes.setMinWidth(500);
        redrawProjectlist();
        
        projectScollbar.setContent(projectNodes);
        projectsPane.setTop(projectsTopPane);
        //projectsPane.setBottom(projectsBottomPane);

        Button addProjectsButton = new Button("Add Project");
        addProjectsButton.setPadding(new Insets(5));
        addProjectsButton.setOnAction(e->{
            primaryStage.setScene(addProjectScene);   
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });  
        
        Button logoutButton2 = new Button("Logout");
        logoutButton2.setPadding(new Insets(5));
        logoutButton2.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });
        
        projectsTopPane.getChildren().addAll(addProjectsButton, logoutButton2);  // 
        //projectsBottomPane.getChildren().addAll();
        
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
        
        Button projectsListButton = new Button("Projects");  
        projectsListButton.setPadding(new Insets(5));
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
        projectTopPaneTimecards.getChildren().addAll(projectsListButton, logoutButton, projectLabel);
        
        projectTopPane.getChildren().addAll(projectTopPaneTimecards);
        projectPane.setTop(projectTopPane);
        //projectPane.setBottom(projectInfoPane);
        
        projectScene = new Scene(projectPane, 750, 250);
        
        // add ProjectScene
        
        VBox newProjectPane = new VBox(0);
        
//        HBox newProject = new HBox(0);
//        newProject.setPadding(new Insets(0));
              
        TextField newProjectNameInput = new TextField();
        newProjectNameInput.setPrefWidth(240);
        Label newProjectNameLabel = new Label("Project Name");
        newProjectNameLabel.setPrefWidth(240);

        VBox nameInput = new VBox();
        nameInput.setStyle(cssLayoutTestBorder);
        nameInput.getChildren().addAll(newProjectNameLabel, newProjectNameInput);
        
        Label newEtcDaysLabel = new Label("Days(=8h)");
        newEtcDaysLabel.setPrefWidth(80);
        Label newEtcHoursLabel = new Label("Hours");
        newEtcHoursLabel.setPrefWidth(80);
        Label newEtcMinutesLabel = new Label("Minutes");
        newEtcMinutesLabel.setPrefWidth(80);
        TextField newEtcDaysInput = new TextField();  
        newEtcDaysInput.setPrefWidth(80);
        TextField newEtcHoursInput = new TextField();  
        newEtcHoursInput.setPrefWidth(80);
        TextField newEtcMinutesInput = new TextField();  
        newEtcMinutesInput.setPrefWidth(80);

        VBox etcInputD = new VBox();
        etcInputD.setStyle(cssLayoutTestBorder);
        etcInputD.getChildren().addAll(newEtcDaysLabel, newEtcDaysInput);
        
        VBox etcInputH = new VBox();
        etcInputH.setStyle(cssLayoutTestBorder);
        etcInputH.getChildren().addAll(newEtcHoursLabel, newEtcHoursInput);
        
        VBox etcInputM = new VBox();
        etcInputM.setStyle(cssLayoutTestBorder);
        etcInputM.getChildren().addAll(newEtcMinutesLabel, newEtcMinutesInput);

        HBox projectInfo01 = new HBox(0);
        projectInfo01.setPadding(new Insets(0));
        projectInfo01.getChildren().addAll(etcInputD, etcInputH, etcInputM);
        
        // Label projectCreationMessage = new Label();
        
        // newProject.getChildren().addAll(nameInput, projectInfo01);
        
        VBox projectInfo00 = new VBox();
        projectInfo00.setStyle(cssLayoutTestBorder);
        projectInfo00.getChildren().addAll(nameInput, projectInfo01);
        
        Button addProjectButton = new Button("Add Project");
        addProjectButton.setPadding(new Insets(5));

        addProjectButton.setOnAction(e->{
            String name = newProjectNameInput.getText();
            int etc = Integer.parseInt(newEtcDaysInput.getText())*8*60+
                    Integer.parseInt(newEtcHoursInput.getText())*60+
                    Integer.parseInt(newEtcMinutesInput.getText());
            if (timecardService.addProject(name, etc)){
//                Success!
                primaryStage.setScene(projectsListScene);
                redrawProjectlist();
            } else {
//                if something went wrong  
            }
        });
        
        HBox topNavigation = new HBox();
        topNavigation.setPadding(new Insets(0));
        topNavigation.getChildren().addAll(projectsListButton, logoutButton);
        
        newProjectPane.getChildren().addAll(topNavigation, projectInfo00, addProjectButton); 
       
        addProjectScene = new Scene(newProjectPane, 600, 550);               
 
        
        // Project summary
        
        projectSummaryPane = new VBox();
        
        Button buttonTimecards = new Button("Timecards");
        buttonTimecards.setOnAction(e->{
            //this.selectedProject = project;
            primaryStage.setScene(projectScene);
            redrawProject();
        });
        
        Button projectsListButton02 = new Button("Projects");  
        projectsListButton02.setPadding(new Insets(5));
        projectsListButton02.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        HBox summaryTopLabel = new HBox(0);
        summaryTopLabel.setPadding(new Insets(0,0,0,0));
        summaryTopLabel.getChildren().addAll(projectsListButton02, buttonTimecards); // project(selectedProject)
        
        if(selectedProject!=null){
            System.out.println("oikeesti");
            projectSummaryPane.getChildren().addAll(summaryTopLabel, project(selectedProject));
        } else {
            projectSummaryPane.getChildren().addAll(summaryTopLabel);
        }  

             
        projectSummaryScene = new Scene(projectSummaryPane, 750, 250);
        
        // setup primary stage
        
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
