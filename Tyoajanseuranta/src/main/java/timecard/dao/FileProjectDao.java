package timecard.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import timecard.domain.Project;

public class FileProjectDao implements ProjectDao {
    public List<Project> projects;
    private String file;
    
    public FileProjectDao(String file) throws Exception {
        projects = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                int etc = Integer.parseInt(parts[2]);
                Project project = new Project(id, parts[1], etc);
                projects.add(project);
            }
        } catch (Exception e) {
            //System.out.println("file:  " +file);
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }        
    }
    
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Project project : projects) {
                writer.write(project.getId() + ";" + project.getName() + ";" + project.getEtc() + "\n");
            }
        }
    }    
    
    private int generateId() {
        return projects.size() + 1;
    }
    
    @Override
    public List<Project> getAll() {
        return projects;
    }
    
    @Override
    public Project add(Project project) throws Exception {
        project.setId(generateId());
        projects.add(project);
        save();
        return project;
    }
}
