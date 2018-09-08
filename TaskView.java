import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;

public class TaskView extends JPanel {

	private TaskModel taskModel;
	private ProjectModel projectModel;
	private MainScreen screen;
	private Column columnPanel;
	private Task task;
	public ArrayList<String> titleList;
	private JComboBox titleBox;

	public TaskView(MainScreen screen, Column columnPanel, ProjectModel model) {
		taskModel = new TaskModel();
		this.projectModel = model;
		this.screen = screen;
		this.columnPanel = columnPanel;
		titleList = new ArrayList<>();
	}
	
	public Task getTask() {
		return task;
	}
	public void display() {
		JFrame frame = new JFrame();
		Dimension loginD = new Dimension(800, 700);
		frame.setPreferredSize(loginD);
		JDialog dialog = new JDialog(frame, "Create New Task");
		dialog.setBounds(70, 70, 800, 800);
		BoxLayout box = new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS);
		dialog.setLayout(box);

		JPanel p1 = new JPanel();
		JLabel name = new JLabel("Name");
		JTextField nameBox = new JTextField("Enter Task Name Here");
		nameBox.setForeground(Color.GRAY);

		nameBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				nameBox.setForeground(Color.BLACK);

				nameBox.setText("");
			}
		});
		
		p1.add(name);
		p1.add(nameBox);
		dialog.add(p1);

		JPanel p2 = new JPanel();
		JLabel description = new JLabel("Description: ");
		JTextField descriptionBox = new JTextField("Enter Description Here");
		descriptionBox.setForeground(Color.GRAY);

		descriptionBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				descriptionBox.setForeground(Color.BLACK);

				descriptionBox.setText("");
			}
		});

		p2.add(description);
		p2.add(descriptionBox);
		dialog.add(p2);

		JPanel p3 = new JPanel();
		JLabel status = new JLabel("Status");
		
		titleBox = new JComboBox(projectModel.getColumnNames().toArray());
		titleBox.setSelectedIndex(projectModel.getColumns().indexOf(columnPanel));
		p3.add(status);
		p3.add(titleBox);
		dialog.add(p3);
		JPanel p4 = new JPanel();
		JLabel dueDate = new JLabel("Due Date: ");

		JTextField dueDateBox = new JTextField("mm/dd/yy");
		dueDateBox.setForeground(Color.GRAY);
		dueDateBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dueDateBox.setForeground(Color.BLACK);

				dueDateBox.setText("");
			}
		});

		p4.add(dueDate);
		p4.add(dueDateBox);
		dialog.add(p4);
		JPanel p6 = new JPanel();
		task = new Task();
		task.setTaskView(this); //adding taskView for serialization
		task.setTaskModel(taskModel);
		JColorChooser picker = new JColorChooser(task.getBackground());
		picker.getSelectionModel().addChangeListener(t -> {
			Color newColor = picker.getColor();
			task.setBackground(newColor);
		});
		p6.add(picker);
		dialog.add(p6);
		JPanel p5 = new JPanel();
		JButton create = new JButton("Create");
		create.addActionListener(e -> {
			screen.getFileSave().setFlag(true);				//Saver flag set true due to change
			JLabel nameLabel = new JLabel(nameBox.getText());
			taskModel.setTaskName(nameBox.getText());
			nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
			JLabel descriptionLabel = new JLabel(descriptionBox.getText());
			taskModel.setDescription(descriptionBox.getText());
			JLabel dueDateLabel = new JLabel(dueDateBox.getText());
			taskModel.setDueDate(dueDateBox.getText());
			task.initializeTaskFields(nameLabel, descriptionLabel, dueDateLabel);
			for(Column item: projectModel.getColumns()) {
				if(item.title.equals(titleBox.getSelectedItem())) {
					columnPanel = item;
				}
			}
			for(int i = 0; i < columnPanel.getTaskPanels().size();i++) {
				columnPanel.remove(columnPanel.getTaskPanels().get(i));
			}
			
			// want to sort this list based on its each tasks taskModel
			columnPanel.addTaskPanel(task); // adding new task panel to the column taskPanel list
			
			// task model list is sorted
			Comparator<TaskModel> list = new CompareTaskModels();
			Collections.sort(columnPanel.getTasks(), list);
			
			// sort task panels list based on task model list
			Collections.sort(columnPanel.getTaskPanels());
			
			for(int i = 0; i < columnPanel.getTaskPanels().size(); i++) {
				columnPanel.add(columnPanel.getTaskPanels().get(i));
			}
			this.getTask().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					displayEditScreen();
				}
			});
			screen.revalidate();
			screen.repaint();
			dialog.setVisible(false);
			dialog.dispose();
		});
		JButton cancel = new JButton("Cancel");
		p5.add(create);
		p5.add(cancel);
		dialog.add(p5);
		dialog.setVisible(true);
		cancel.addActionListener(e -> {
			dialog.setVisible(false);
			dialog.dispose();

		});


	}
	
	public void displayEditScreen() {

		JFrame frame = new JFrame();
		Dimension loginD = new Dimension(800, 600);
		frame.setPreferredSize(loginD);
		JDialog dialog = new JDialog(frame, "Create New Task");
		dialog.setBounds(70, 70, 800, 800);
		BoxLayout box = new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS);
		dialog.setLayout(box);

		JPanel p1 = new JPanel();
		JLabel name = new JLabel("Name");
		JTextField nameBox = new JTextField(task.taskNameLabel.getText());
		p1.add(name);
		p1.add(nameBox);
		dialog.add(p1);

		JPanel p2 = new JPanel();
		JLabel description = new JLabel("Description: ");
		JTextField descriptionBox = new JTextField(task.taskDescriptionLabel.getText());


		p2.add(description);
		p2.add(descriptionBox);
		dialog.add(p2);

		JPanel p3 = new JPanel();
		JLabel status = new JLabel("Status");
		p3.add(status);
		p3.add(titleBox);
		dialog.add(p3);

		JPanel p4 = new JPanel();
		JLabel dueDate = new JLabel("Due Date: ");

		JTextField dueDateBox = new JTextField(task.taskDueDateLabel.getText());
		dueDateBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dueDateBox.setText("");
			}
		});

		p4.add(dueDate);
		p4.add(dueDateBox);
		dialog.add(p4);
		
		JPanel p6 = new JPanel();
		JColorChooser picker2 = new JColorChooser(task.getBackground());
		picker2.getSelectionModel().addChangeListener(t -> {
			Color newColor = picker2.getColor();
			task.setBackground(newColor);
		});
		p6.add(picker2);
		dialog.add(p6);
		JPanel p5 = new JPanel();
		JButton edit = new JButton("Save Changes");
		edit.addActionListener(editButton -> {
			if(titleBox.getSelectedItem() != columnPanel.title) {
				for(Column item: projectModel.getColumns()) {
					if(titleBox.getSelectedItem().equals(item.title)) {
						columnPanel.remove(task);
						columnPanel = item;
						columnPanel.add(task);
						screen.repaint();
					}
				}
			}

			task.setName(nameBox.getText());
			task.setDescription(descriptionBox.getText());
			task.setDueDate(dueDateBox.getText());
			// removes panels from column
			for(int i = 0; i < columnPanel.getTaskPanels().size();i++) {
				columnPanel.remove(columnPanel.getTaskPanels().get(i));
			}
			// sorting taskModel
			Comparator<TaskModel> list = new CompareTaskModels();
			Collections.sort(columnPanel.getTasks(), list);
			// sorts TasksPanels
			Collections.sort(columnPanel.getTaskPanels());
			// puts them back in sorted order
			for(int i = 0; i < columnPanel.getTaskPanels().size(); i++) {
				columnPanel.add(columnPanel.getTaskPanels().get(i));
			}
			
			screen.revalidate();
			screen.repaint();
			dialog.setVisible(false);
			dialog.dispose();

		});
		
		JButton cancel = new JButton("Cancel");
		p5.add(edit);
		p5.add(cancel);
		dialog.add(p5);
		dialog.setVisible(true);
		cancel.addActionListener(cancelButton -> {
			dialog.setVisible(false);
			dialog.dispose();

		});

		task.revalidate();
		task.repaint();
		columnPanel.revalidate();
		columnPanel.repaint();
		screen.revalidate();
		screen.repaint();
	
	}

	public Column getColumnPanel() {
		return columnPanel;
	}
}
