import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Task extends JPanel implements Comparable<Task>{
	private MainScreen mainScreen;
	public JLabel taskNameLabel;
	public JLabel taskDescriptionLabel;
	public JLabel taskDueDateLabel;
	public String columnWithin;
	private TaskModel taskModel;
	private TaskView taskView;
	
	public String getName() {
		return taskNameLabel.getText();
	}
	public void setName(String name) {
		taskNameLabel.setText(name);
	}
	public String getDescription() {
		return taskDescriptionLabel.getText();
	}
	public void setDescription(String desc) {
		taskDescriptionLabel.setText(desc);
	}
	public String getDueDate() {
		return taskDueDateLabel.getText();
	}
	public void setDueDate(String date) {
		taskDueDateLabel.setText(date);
	}
	public Task() {
		
	}
	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
	}
	public TaskModel getTaskModel() {
		return taskModel;
	}
	
	public void setTaskView(TaskView taskView) {
		this.taskView = taskView;
	}
	public TaskView getTaskView() {
		return taskView;
	}
	public Task(JLabel taskNameLabel, JLabel taskDescriptionLabel, JLabel taskDueDateLabel, MainScreen mainScreen) {
		this.mainScreen = mainScreen;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.taskNameLabel = taskNameLabel;
		this.taskDescriptionLabel = taskDescriptionLabel;
		this.taskDueDateLabel = taskDueDateLabel;
		this.add(taskNameLabel);
		this.add(new JLabel("      "));
		this.add(taskDescriptionLabel);
		this.add(new JLabel("      "));
		this.add(taskDueDateLabel);
		this.add(new JLabel("      "));
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
	}
	
	public void initializeTaskFields(JLabel taskNameLabel, JLabel taskDescriptionLabel, JLabel taskDueDateLabel) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.taskNameLabel = taskNameLabel;
		this.taskDescriptionLabel = taskDescriptionLabel;
		this.taskDueDateLabel = taskDueDateLabel;
		this.add(taskNameLabel);
		this.add(new JLabel("      "));
		this.add(taskDescriptionLabel);
		this.add(new JLabel("      "));
		this.add(taskDueDateLabel);
		this.add(new JLabel("      "));
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
	}
	@Override
	public int compareTo(Task t2) {
		String year = this.getDueDate().substring(this.getDueDate().length() -4);
		String secondYear = t2.getDueDate().substring(t2.getDueDate().length() -4);
		if(year.compareTo(secondYear) < 0) {
			return -1;
		} else if(year.compareTo(secondYear) > 0) {
			return 1; 
		}
		
		String month = this.getDueDate().substring(0, 2);
		String secondMonth = t2.getDueDate().substring(0, 2);
		if(month.compareTo(secondMonth) < 0) {
			return -1;
		} else if(month.compareTo(secondMonth) > 0) {
			return 1; 
		}
		
		String day = this.getDueDate().substring(3, 5);
		String secondDay = t2.getDueDate().substring(3, 5);
		return day.compareTo(secondDay);
	}
}

