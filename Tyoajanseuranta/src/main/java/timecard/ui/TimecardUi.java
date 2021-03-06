package timecard.ui;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
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
import javafx.scene.chart.*;

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
    
    private String cssLayoutWhiteBold = "-fx-font-weight: bold;\n" +
                   "-fx-text-fill: #FFFFFF;\n";  ; 
    
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
        label.setStyle(cssLayoutH2);
        label.setMinHeight(0);

        Button button = new Button("Select");
        button.setOnAction(e->{
            this.selectedProject = project;
            primaryStage.setScene(projectScene);
            redrawProject();
        });
        button.setPadding(new Insets(5));
                
        Region spacer05 = new Region();
        HBox.setHgrow(spacer05, Priority.ALWAYS);
        box.setPadding(new Insets(5,5,5,5));
        
        box.setMaxWidth(730);
        box.setMinWidth(730);
        
        box.getChildren().addAll(label, spacer05, button);
        return box;
    }
    
    public Node createTimecardNode(Timecard timecard) {
        HBox box = new HBox(0);

        int t = timecard.getTime();
        int hours = t / 60;
        int minutes = t % 60;
        String timeSting = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";

        Label time  = new Label(timeSting);
        time.setPrefWidth(144);        
        time.setStyle(cssLayoutTestBorder);
        time.setPadding(new Insets(5,5,5,5));
        
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
        
        type.setPrefWidth(162);
        type.setStyle(cssLayoutTestBorder);
        type.setPadding(new Insets(5,5,5,5));
        
        Label description  = new Label(timecard.getDescription());
        description.setPadding(new Insets(5,5,5,5));
        description.setPrefWidth(262);
        description.setStyle(cssLayoutTestBorder);
                
        Label username  = new Label(timecard.getUsername());
        username.setPadding(new Insets(5,5,5,5));
        username.setPrefWidth(150);
        username.setStyle(cssLayoutTestBorder);
        
        time.setMinHeight(28);
        type.setMinHeight(28);
        description.setMinHeight(28);        
        username.setMinHeight(28);
        
        box.getChildren().addAll(time, type, description, username);
        box.setMaxWidth(750);
        box.setMinWidth(750);
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
        
        projectsListButton.setMaxWidth(150);
        projectsListButton.setMinWidth(150);
        
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

        Label timecardCreationMessage = new Label("");
        timecardCreationMessage.setPadding(new Insets(5));
        
        Label addTimecardButtonLabel = new Label(""); 
        
        Button addTimecardButton = new Button("Add Time");
        addTimecardButton.setPadding(new Insets(5));

        addTimecardButton.setOnAction(e->{

            // field validations
            
            if (newTimecardHoursInput.getText().isEmpty() && newTimecardMinutesInput.getText().isEmpty()) {
                timecardCreationMessage.setText("Time is empty!");
            } else if (!newTimecardHoursInput.getText().isEmpty() && !newTimecardHoursInput.getText().matches("[0-9]*")) {
                timecardCreationMessage.setText("Time input numbers only!");            
            } else if (!newTimecardMinutesInput.getText().isEmpty() && !newTimecardMinutesInput.getText().matches("[0-9]*")) {
                timecardCreationMessage.setText("Time input numbers only!");  
            } else if (!newTimecardMinutesInput.getText().isEmpty() && Integer.parseInt(newTimecardMinutesInput.getText()) > 60) {
                timecardCreationMessage.setText("Minutes is too much!");             
            } else if (newTimecardDescriptionInput.getText().length() > 30) {
                timecardCreationMessage.setText("Description is too long. Only 30 characters!");  
            } else {
                
                int timecardHoursMins = 0;
                int timecardMins = 0;
                
                if (!newTimecardMinutesInput.getText().isEmpty()) {
                    timecardMins = Integer.parseInt(newTimecardMinutesInput.getText());
                }
                
                if (!newTimecardHoursInput.getText().isEmpty()) {
                    timecardHoursMins = Integer.parseInt(newTimecardHoursInput.getText())*60;
                }

                int time = timecardHoursMins + timecardMins;
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
                    redrawProject();
                    // timecardCreationMessage.setText("Timecard added!");
                } else {
                    // if something went wrong  
                }  
            }
         });
        
        VBox timeInputH = new VBox();
        timeInputH.setStyle(cssLayoutTestBorder);
        timeInputH.getChildren().addAll(newTimecardHoursLabel, newTimecardHoursInput);
        timeInputH.setPadding(new Insets(5,5,5,5));
        
        VBox timeInputM = new VBox();
        timeInputM.setStyle(cssLayoutTestBorder);
        timeInputM.getChildren().addAll(newTimecardMinutesLabel, newTimecardMinutesInput);
        timeInputM.setPadding(new Insets(5,5,5,5));
        
        VBox typeInput = new VBox();
        typeInput.setStyle(cssLayoutTestBorder);
        typeInput.getChildren().addAll(newTimecardTypeLabel, jobType);
        typeInput.setPadding(new Insets(5,5,5,5));
        
        VBox descriptionInput = new VBox();
        descriptionInput.setStyle(cssLayoutTestBorder);
        descriptionInput.getChildren().addAll(newTimecardDescriptionLabel, newTimecardDescriptionInput);
        descriptionInput.setPadding(new Insets(5,5,5,5));

        VBox addTimecardButtonBox = new VBox();
        addTimecardButtonBox.setStyle(cssLayoutTestBorder);
        addTimecardButtonBox.getChildren().addAll(addTimecardButtonLabel, addTimecardButton);
        addTimecardButtonBox.setPrefWidth(150);
        addTimecardButtonBox.setPadding(new Insets(5,5,5,5));
        
        // add new timecard form
        newTimecardPane.getChildren().addAll(timeInputH, timeInputM, typeInput, descriptionInput, addTimecardButtonBox); 
        
        newTimecardPane.setMaxWidth(748);
        newTimecardPane.setMinWidth(748);
        newTimecardPane.setStyle(cssLayoutBackgroundLightGray);
        
        redrawTimecards();
        
        Button projectSummaryButton = new Button("Project Summary");  
        projectSummaryButton.setPadding(new Insets(5));
        projectSummaryButton.setOnAction(e->{
            primaryStage.setScene(projectSummaryScene);
            redrawProjectSummary();
        });
        
        projectSummaryButton.setMaxWidth(150);
        projectSummaryButton.setMinWidth(150);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        }); 
        
        logoutButton.setMaxWidth(150);
        logoutButton.setMinWidth(150);
        
        projectTopPane.getChildren().clear();
        
        Region spacer04 = new Region();
        spacer04.setPrefWidth(100);
        HBox.setHgrow(spacer04, Priority.ALWAYS);
        
        Label addTimeLabel = new Label("Timecards");
        addTimeLabel.setPadding(new Insets(5,5,5,5));
        addTimeLabel.setMaxWidth(200);
        addTimeLabel.setMinWidth(200);
        addTimeLabel.setStyle(cssLayoutWhiteBold);
        
        HBox topLabel = new HBox(0);
        topLabel.setPadding(new Insets(0));      
        topLabel.setStyle(cssLayoutTopNavigation);
        topLabel.setPadding(new Insets(5,5,5,5));
        topLabel.getChildren().addAll(addTimeLabel, projectsListButton, projectSummaryButton, spacer04, logoutButton);

        VBox addTime = new VBox();
        addTime.setPadding(new Insets(0,0,0,0));
        addTime.getChildren().addAll(newTimecardPane);
        addTime.setStyle(cssLayoutTestBorder);
        
        HBox projectName = new HBox();
        projectName.getChildren().addAll(project(selectedProject), timecardCreationMessage);

        projectTopPane.getChildren().addAll(topLabel, projectName, addTime);
    }
    
    public void redrawProjectSummary() {
        
        projectSummaryPane.getChildren().clear();
        
        Button buttonTimecards = new Button("Timecards");
        buttonTimecards.setPadding(new Insets(5));
        buttonTimecards.setOnAction(e->{
            primaryStage.setScene(projectScene);
            redrawProject();
        });
        
        buttonTimecards.setMaxWidth(150);
        buttonTimecards.setMinWidth(150);
        
        Button projectsListButton02 = new Button("Projects List");  
        projectsListButton02.setPadding(new Insets(5));
        projectsListButton02.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        projectsListButton02.setMaxWidth(150);
        projectsListButton02.setMinWidth(150);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });
       
        logoutButton.setMaxWidth(150);
        logoutButton.setMinWidth(150);
        
        Label summary = new Label("Project Summary");
        summary.setPadding(new Insets(5,5,5,5));
        summary.setMaxWidth(200);
        summary.setMinWidth(200);
        summary.setStyle(cssLayoutWhiteBold);
        
        Region spacer00 = new Region();
        spacer00.setPrefWidth(200);
        HBox.setHgrow(spacer00, Priority.ALWAYS);

        HBox topLabel = new HBox(0);
        topLabel.setStyle(cssLayoutTopNavigation);
        topLabel.setPadding(new Insets(5,5,5,5));
        topLabel.getChildren().addAll(summary, projectsListButton02, buttonTimecards, spacer00, logoutButton);
        
        // job types and total times
        
        VBox summaryPane = new VBox();
        summaryPane.setPadding(new Insets(5,5,5,5));
        summaryPane.setPrefWidth(400);

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
        
        Label etcTime  = new Label(selectedProject.getEtcString());
        etcTime.setMinHeight(0);
        etcTime.setPrefWidth(100);
        etcTime.setPadding(new Insets(0));
        
        Label etcTimeTitle  = new Label("ETC:");
        etcTimeTitle.setMinHeight(0);
        etcTimeTitle.setPrefWidth(100);
        
        HBox etcTimeRow = new HBox();
        etcTimeRow.setMinHeight(0);
        etcTimeRow.setPrefWidth(100);
        etcTimeRow.getChildren().addAll(etcTimeTitle, etcTime);
        etcTimeRow.setPadding(new Insets(15,0,0,0));
        
        summaryPane.getChildren().addAll(project(selectedProject), notselectedTimeRow, designTimeRow, programmingTimeRow, testingTimeRow, maintenanceTimeRow, totalTimeRow, etcTimeRow);
        
        // Graph
        
        VBox graphPane = new VBox(0);
        
        int jobNotSelected = 0;
        int jobDesign = 0;
        int jobProgramming = 0;
        int jobTesting = 0;
        int jobMaintenance = 0;
        
        jobNotSelected = timecardService.getProjectJobTypeTimeInt(selectedProject.getId(),0);
        jobDesign = timecardService.getProjectJobTypeTimeInt(selectedProject.getId(),1);
        jobProgramming = timecardService.getProjectJobTypeTimeInt(selectedProject.getId(),2);
        jobTesting = timecardService.getProjectJobTypeTimeInt(selectedProject.getId(),3);
        jobMaintenance = timecardService.getProjectJobTypeTimeInt(selectedProject.getId(),4);
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Not Selected", jobNotSelected),
                new PieChart.Data("Design", jobDesign),
                new PieChart.Data("Programming", jobProgramming),
                new PieChart.Data("Testing", jobTesting),
                new PieChart.Data("Maintenance", jobMaintenance));
        final PieChart chart = new PieChart(pieChartData);
        chart.setLabelLineLength(10);
        chart.setLegendVisible(false);
        chart.setLegendSide(Side.LEFT);
        chart.setTitle("Job Types");


        graphPane.getChildren().addAll(chart);
        
        HBox summaryAndGraph = new HBox();
        summaryAndGraph.getChildren().addAll(summaryPane, graphPane);
       
        projectSummaryPane.getChildren().addAll(topLabel, summaryAndGraph);
    }
    
    public Node project (Project project) {
        HBox box = new HBox();
        Label projectName  = new Label(selectedProject.getName());
        projectName.setMinHeight(0);
        projectName.setStyle(cssLayoutH2);
        projectName.setPrefWidth(300);
        projectName.setPadding(new Insets(5,5,5,5));

        box.setPadding(new Insets(0,0,0,0));
        box.getChildren().addAll(projectName);
        
        // Timecard
        
        return box;       
    }
      
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;        

        // login scene
        
        VBox loginPane = new VBox(0);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(0));
        inputPane.setPadding(new Insets(5, 5, 5, 5));
        Label loginLabel = new Label("Username");
        TextField usernameInput = new TextField();
        usernameInput.setPadding(new Insets(5,5,5,5));
                
        
        Label loginMessage = new Label();
        
        Button loginButton = new Button("Login");
        
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
        loginButton.setPadding(new Insets(5,5,5,5));
        
        Button createButton = new Button("Create New User");
        createButton.setOnAction(e->{
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);   
        });
        createButton.setPadding(new Insets(5,5,5,5));
        createButton.setMaxWidth(150);
        createButton.setMinWidth(150);
        
        inputPane.getChildren().addAll(loginLabel, usernameInput, loginButton);
        
        VBox loginButtons = new VBox(10);
        loginButtons.setPadding(new Insets(5,5,5,5));
        loginButtons.getChildren().addAll(loginMessage, inputPane); 
        
        Label topLoginLabel = new Label("Login");
        topLoginLabel.setPadding(new Insets(5,5,5,5));
        topLoginLabel.setMaxWidth(200);
        topLoginLabel.setMinWidth(200);
        topLoginLabel.setStyle(cssLayoutWhiteBold);

        Region spacer08 = new Region();
        spacer08.setPrefWidth(400);
        HBox.setHgrow(spacer08, Priority.ALWAYS);
        
        HBox topLoginLabelBox = new HBox();
        topLoginLabelBox.setStyle(cssLayoutTopNavigation);
        topLoginLabelBox.setPadding(new Insets(5,5,5,5));
        topLoginLabelBox.getChildren().addAll(topLoginLabel, spacer08, createButton); 
        
        loginPane.getChildren().addAll(topLoginLabelBox, loginButtons);       
        
        loginScene = new Scene(loginPane, 750, 250);
                
        // new createNewUserScene
        
        VBox newUserPane = new VBox(5);
        newUserPane.setPadding(new Insets(0));
        
        HBox newUsernamePane = new HBox(5);
        newUsernamePane.setPadding(new Insets(5));
        
        TextField newUsernameInput = new TextField(); 
        Label newUsernameLabel = new Label("Username");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
     
        HBox newNamePane = new HBox(5);
        newNamePane.setPadding(new Insets(5));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("Name");
        newNameLabel.setPrefWidth(100);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);        
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = new Button("Create");
        createNewUserButton.setPadding(new Insets(5));

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
        
        VBox newUserFormPane = new VBox(5);
        newUserFormPane.setPadding(new Insets(5));
        newUserFormPane.getChildren().addAll(newUsernamePane, newNamePane, createNewUserButton);
        
        
        Label newUserlabel = new Label("New User");
        newUserlabel.setPadding(new Insets(5,5,5,5));
        newUserlabel.setMaxWidth(200);
        newUserlabel.setMinWidth(200);
        newUserlabel.setStyle(cssLayoutWhiteBold);
        
        HBox topNewUser = new HBox();
        topNewUser.setStyle(cssLayoutTopNavigation);
        topNewUser.setPadding(new Insets(5,5,5,5));
        
        Button logoutButton3 = new Button("Back To Login");
        logoutButton3.setPadding(new Insets(5));
        logoutButton3.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });
        
        logoutButton3.setMaxWidth(150);
        logoutButton3.setMinWidth(150);

        Region spacer07 = new Region();
        spacer07.setPrefWidth(500);
        HBox.setHgrow(spacer07, Priority.ALWAYS);
        
        topNewUser.getChildren().addAll(newUserlabel, spacer07, logoutButton3); 
        
        
        newUserPane.getChildren().addAll(topNewUser, userCreationMessage, newUserFormPane); 
       
        newUserScene = new Scene(newUserPane, 750, 250);  
   
        // Projects list scene
        
        HBox projectsTopPane = new HBox(0);

        // List projects here
        
        ScrollPane projectScollbar = new ScrollPane();
        BorderPane projectsPane = new BorderPane(projectScollbar);

        projectNodes = new VBox(0);
        projectNodes.setMaxWidth(500);
        projectNodes.setMinWidth(500);
        redrawProjectlist();
        
        projectScollbar.setContent(projectNodes);
        projectsPane.setTop(projectsTopPane);

        Button addProjectsButton = new Button("Add Project");
        addProjectsButton.setPadding(new Insets(5));
        addProjectsButton.setOnAction(e->{
            primaryStage.setScene(addProjectScene);   
        });
        
        addProjectsButton.setMaxWidth(150);
        addProjectsButton.setMinWidth(150);
        
        Button logoutButton2 = new Button("Logout");
        logoutButton2.setPadding(new Insets(5));
        logoutButton2.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });
        
        logoutButton2.setMaxWidth(150);
        logoutButton2.setMinWidth(150);
        
        projectsTopPane.setStyle(cssLayoutTopNavigation);
        projectsTopPane.setPadding(new Insets(5,5,5,5));
        
        Region spacer01 = new Region();
        spacer01.setPrefWidth(500);
        HBox.setHgrow(spacer01, Priority.ALWAYS);
        
        Label projectsListlabel = new Label("Projects List");
        projectsListlabel.setPadding(new Insets(5,5,5,5));
        projectsListlabel.setMaxWidth(200);
        projectsListlabel.setMinWidth(200);
        projectsListlabel.setStyle(cssLayoutWhiteBold);
        
        projectsTopPane.getChildren().addAll(projectsListlabel, addProjectsButton, spacer01, logoutButton2);  // 
        
        projectsListScene = new Scene(projectsPane, 750, 250);

        // Project scene
               
        projectTopPane = new VBox(0);
        
        ScrollPane projectTimecardScollbar = new ScrollPane();
        BorderPane projectPane = new BorderPane(projectTimecardScollbar);

        String projectName = "";
        if(selectedProject!= null) { 
            projectName = selectedProject.getName(); 
        }
        
        Label projectLabel  = new Label(projectName);
        
        projectLabel.setStyle(cssLayoutH2);
        
        Button projectsListButton = new Button("Projects List");  
        projectsListButton.setPadding(new Insets(5));
        projectsListButton.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        projectsListButton.setMaxWidth(150);
        projectsListButton.setMinWidth(150);
        
        // List timecards
        
        timecardNodes = new VBox(0);
        timecardNodes.setMaxWidth(700);
        timecardNodes.setMinWidth(700);
        
        Region spacer06 = new Region();
        spacer06.setPrefWidth(400);
        VBox.setVgrow(spacer06, Priority.ALWAYS);
        
        projectTimecardScollbar.setContent(timecardNodes);

        Button logoutButton = new Button("Logout");
        logoutButton.setPadding(new Insets(5));
        logoutButton.setOnAction(e->{
            timecardService.logout();
            primaryStage.setScene(loginScene);
        });  
       
        logoutButton.setMaxWidth(150);
        logoutButton.setMinWidth(150);
        
        VBox projectTopPaneTimecards = new VBox(0);
        projectTopPaneTimecards.getChildren().addAll(projectsListButton, spacer06, logoutButton);
        
        projectTopPane.getChildren().addAll(projectTopPaneTimecards);
        projectPane.setTop(projectTopPane);
        
        projectScene = new Scene(projectPane, 750, 250);
        
        // add ProjectScene
        
        VBox newProjectPane = new VBox(0);
        
        TextField newProjectNameInput = new TextField();
        newProjectNameInput.setPrefWidth(240);
        newProjectNameInput.setPadding(new Insets(5));
        Label newProjectNameLabel = new Label("Project Name");
        newProjectNameLabel.setPrefWidth(240);
        newProjectNameLabel.setPadding(new Insets(0));

        VBox nameInput = new VBox();

        nameInput.getChildren().addAll(newProjectNameLabel, newProjectNameInput);
        
        Label newEtcLabel = new Label("Estimated Time To Complete (ETC)");
        newEtcLabel.setPadding(new Insets(15,0,5,0));
        newEtcLabel.setPrefWidth(400);
        
        Label newEtcDaysLabel = new Label("Days(=8h)");
        newEtcDaysLabel.setPrefWidth(80);
        Label newEtcHoursLabel = new Label("Hours");
        newEtcHoursLabel.setPrefWidth(80);
        Label newEtcMinutesLabel = new Label("Minutes");
        newEtcMinutesLabel.setPrefWidth(80);
        TextField newEtcDaysInput = new TextField();  
        newEtcDaysInput.setPrefWidth(80);
        newEtcDaysInput.setPadding(new Insets(5));
        TextField newEtcHoursInput = new TextField();  
        newEtcHoursInput.setPrefWidth(80);
        newEtcHoursInput.setPadding(new Insets(5));
        TextField newEtcMinutesInput = new TextField();  
        newEtcMinutesInput.setPrefWidth(80);
        newEtcMinutesInput.setPadding(new Insets(5));

        VBox etcInputD = new VBox();
        etcInputD.getChildren().addAll(newEtcDaysLabel, newEtcDaysInput);
        
        VBox etcInputH = new VBox();
        etcInputH.getChildren().addAll(newEtcHoursLabel, newEtcHoursInput);
        
        VBox etcInputM = new VBox();
        etcInputM.getChildren().addAll(newEtcMinutesLabel, newEtcMinutesInput);

        HBox projectInfo01 = new HBox(0);
        projectInfo01.setPadding(new Insets(0));
        projectInfo01.getChildren().addAll(etcInputD, etcInputH, etcInputM);
        
        Label projectCreationMessage = new Label("");
        projectCreationMessage.setPadding(new Insets(5,5,5,5));
        
        VBox projectInfo00 = new VBox();

        projectInfo00.getChildren().addAll(nameInput, newEtcLabel, projectInfo01);
        projectInfo00.setPrefWidth(400);
        projectInfo00.setMaxWidth(400);
        projectInfo00.setMinWidth(400);
        projectInfo00.setPadding(new Insets(5,5,5,5));
        
        Button addProjectButton = new Button("Add Project");
        addProjectButton.setPadding(new Insets(5));
        addProjectButton.setMaxWidth(150);
        addProjectButton.setMinWidth(150);

        int days = 0;
        
        addProjectButton.setOnAction(e->{
            if (newProjectNameInput.getText().isEmpty()) {
                projectCreationMessage.setText("No project name!");
            } else if (!newEtcDaysInput.getText().isEmpty() && !newEtcDaysInput.getText().matches("[0-9]*")) {
                projectCreationMessage.setText("Time input numbers only!"); 
            } else if (!newEtcHoursInput.getText().isEmpty() && !newEtcHoursInput.getText().matches("[0-9]*")) {
                projectCreationMessage.setText("Time input numbers only!");            
            } else if (!newEtcMinutesInput.getText().isEmpty() && !newEtcMinutesInput.getText().matches("[0-9]*")) {
                projectCreationMessage.setText("Time input numbers only!");  
            } else if (!newEtcMinutesInput.getText().isEmpty() && Integer.parseInt(newEtcMinutesInput.getText()) > 60) {
                projectCreationMessage.setText("Minutes is too much!");             
            } else if (newProjectNameInput.getText().length() > 30) {
                projectCreationMessage.setText("project name is too long. Only 30 characters!");  
            } else {
                String name = newProjectNameInput.getText();
                
                int etcDaysMins = 0;
                int etcHoursMins = 0;
                int etcMins = 0;
                
                if (!newEtcDaysInput.getText().isEmpty()) {
                    etcDaysMins = Integer.parseInt(newEtcDaysInput.getText())*8*60;
                }
                
                if (!newEtcHoursInput.getText().isEmpty()) {
                    etcHoursMins = Integer.parseInt(newEtcHoursInput.getText())*60;
                }
                
                if (!newEtcMinutesInput.getText().isEmpty()) {
                    etcMins = Integer.parseInt(newEtcMinutesInput.getText());
                }
                
                int etc = etcDaysMins + etcHoursMins + etcMins;
                
                if (timecardService.addProject(name, etc)){
//                Success!
                    primaryStage.setScene(projectsListScene);
                    redrawProjectlist();
                } else {
//                if something went wrong  
                }
            }
        });
        
        HBox buttonArea = new HBox();
        buttonArea.setPadding(new Insets(5));
        buttonArea.getChildren().addAll(addProjectButton);
        
        Region spacer02 = new Region();
        spacer02.setPrefWidth(500);
        VBox.setVgrow(spacer02, Priority.ALWAYS);
        
        Label addProjectlabel = new Label("Add Project");
        addProjectlabel.setPadding(new Insets(5,5,5,5));
        addProjectlabel.setMaxWidth(200);
        addProjectlabel.setMinWidth(200);
        addProjectlabel.setStyle(cssLayoutWhiteBold);
        
        HBox topNavigation = new HBox();
        topNavigation.setStyle(cssLayoutTopNavigation);
        topNavigation.setPadding(new Insets(5,5,5,5));
        topNavigation.getChildren().addAll(addProjectlabel, projectsListButton, spacer02, logoutButton);
        
        newProjectPane.getChildren().addAll(topNavigation, projectCreationMessage, projectInfo00, buttonArea); 
       
        addProjectScene = new Scene(newProjectPane, 750, 250);               

        // Project summary
        
        projectSummaryPane = new VBox();
        
        Button buttonTimecards = new Button("Timecards");
        buttonTimecards.setOnAction(e->{
            primaryStage.setScene(projectScene);
            redrawProject();
        });
        
        Button projectsListButton02 = new Button("Projects List");  
        projectsListButton02.setPadding(new Insets(5));
        projectsListButton02.setOnAction(e->{
            primaryStage.setScene(projectsListScene);   
        });
        
        Region spacer03 = new Region();
        spacer03.setPrefWidth(400);
        HBox.setHgrow(spacer03, Priority.ALWAYS);
        
        HBox summaryTopLabel = new HBox(0);
        summaryTopLabel.setPadding(new Insets(0,0,0,0));
        summaryTopLabel.getChildren().addAll(projectsListButton02, spacer03, buttonTimecards);
        
        if(selectedProject!=null){
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
