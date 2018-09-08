import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class FileSave implements Serializable{
	private TaskBoardModel taskBoard;
	private Boolean flag = false;
	private String fileName = "null";
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	// dirty flag to see if anything changed, for now only if new project created
	// the flag should be set true when: a task is made, a new project is made, a task is edited, and when a project is edited
	// The flag should be set false after the save button is pressed.
	public void setFlag(Boolean flag) {
		this.flag = flag; 
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	// takes the taskboardmodel you want to save
	public FileSave(TaskBoardModel taskBoard) {
		this.taskBoard = taskBoard;	
	}
	// saves the taskboardmodel in the fileName
	// Should save all projects when the save button or logout button is clicked.
	public void readFile() throws FileNotFoundException, IOException {
		out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(taskBoard);
		for(int i = 0; i < taskBoard.getProjects().size(); i++) {
			System.out.println(taskBoard.getProjects().get(i).getName() + " was saved.");
		}
		this.flag = false;
		System.out.println();
		out.close();
	}
	
	// takes the taskboardmodel out of the Stream and reinstantiates file saves taskboardmodel variable
	// Should load when the load button is clicked and the second time logging in. 
	public void loadFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		in = new ObjectInputStream(new FileInputStream(fileName));
		taskBoard = (TaskBoardModel)in.readObject();
		System.out.println("Saver's task board has the following projects: ");
		for(int i = 0; i < taskBoard.getProjects().size(); i++) {
			System.out.println(taskBoard.getProjects().get(i).getName() + " as a project.");
		}
		in.close();
	}

	public TaskBoardModel getTaskBoard() {
		return taskBoard;
	}

	public Boolean getFlag() {
		return flag;
	}
}
