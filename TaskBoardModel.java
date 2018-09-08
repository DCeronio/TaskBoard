import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// made serializable
public class TaskBoardModel implements Serializable {
	
	private  String name;
	private  List<ProjectModel> projects;
	private List<String> projectTitles; // for project selector for automatic load/ load
	private  String fileName;
	private  String correctUsername;
	private  String correctPassword;
	private  boolean ableToLogin;
	
	public TaskBoardModel() {
		projects = new ArrayList<>();
		projectTitles = new ArrayList<>(); // imitialized
		this.correctUsername = "admin";
		this.correctPassword = "admin";
	}
	
	public List<String> getProjectTitles() {
		return projectTitles;
	}
	public boolean checkLogin(String username, String password) {
		return this.correctUsername.equals(username) && this.correctPassword.equals(password);
	
	}
	
	public TaskBoardModel getInstance() {
		return this;
	}

	public void addProject(ProjectModel project) {
		projects.add(project);
		
	}
	
	public List<ProjectModel> getProjects() {
		return projects;
	}
}
