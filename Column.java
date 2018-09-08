import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import javax.swing.*;



public class Column extends JPanel implements Serializable{

	

	public String title;

	private JLabel label; 

	private JButton addTaskButton;
	private ArrayList<Task> taskPanels;
	private ArrayList<TaskModel> tasks;

	

	public Column(String title) {
		taskPanels = new ArrayList<>();
		tasks = new ArrayList<>();

		this.title = title;

		addTaskButton = new JButton("+");

		addTaskButton.setVisible(true);

		label = new JLabel(title);

		label.setFont(new Font("Arial", Font.BOLD, 15));

		label.setVisible(true);

		this.add(label);

		this.add(addTaskButton);

	}

	public ArrayList<Task> getTaskPanels() {
		return taskPanels;
	}

	public void addButton() {

		this.add(addTaskButton = new JButton("+"));

	}

	

	public void setTitle(String title) {

		this.title = title;

		label = new JLabel(title);

		label.setFont(new Font("Arial", Font.BOLD, 15));

	}

	

	public ArrayList<TaskModel> getTasks() {

		return tasks;

	}

	public String getTitle() {

		return title;

	}

	

	public JLabel getLabel() {

		return label;

	}

	

	public void addLabel() {

		this.add(label);

	}

	

	public void remove(TaskModel m) {

		tasks.remove(m);

	}

	

	public JButton getAddTaskButton() {

		return addTaskButton;


	}

	public void addTaskPanel(Task taskPanel) {
		taskPanels.add(taskPanel);
	}

	public void addTask(TaskModel model) {

		tasks.add(model);
		
		Comparator<TaskModel> list = new CompareTaskModels();

		Collections.sort(tasks, list);

	}
}