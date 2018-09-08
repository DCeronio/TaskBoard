import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

// made serializable
public class ProjectModel implements Serializable{
	private String name;
	private List<String> columnNames; 
	private List<Column> columns;
	//private List<TaskModel> task;
	
	public ProjectModel() {
		columns = new ArrayList<>();
		//task = new ArrayList<>();
		columnNames = new ArrayList<>();
	}

	public void setName(String name) {

		this.name = name;
		
	}

	public String getName() {

		return name;

	}	

	public void deleteColumn(String column) {

		columns.remove(column);

	}

	public void addColumn(Column column) {

		columns.add(column);

	}

	/*public void addTask(TaskModel t) {

		task.add(t);

	}*/
	
	public List<Column> getColumns() {

		return columns;

	}
	
	public void addColumnName(String s) {
		columnNames.add(s);
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

}

