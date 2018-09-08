import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.util.ArrayList;

import java.io.*;

public class MainScreen extends JPanel {

	private ProjectView projectView;
	private FileSave saver; // file saver instance variable
	public JFrame frame;
	private JPanel mainScreenButtons = new JPanel();
	private JPanel columns = new JPanel(new GridLayout());
	private TaskBoardModel model = new TaskBoardModel();
	private JButton logout;
	private JButton createNew;
	private JButton load;
	private JButton delete;
	private JButton save;
	private JButton edit;
	public JComboBox select;

	// fileSave getter
	public FileSave getFileSave() {
		return saver;
	}

	public MainScreen(JFrame frame) {
		this.frame = frame;
		saver = new FileSave(model); // file saver instantiated
		projectView = new ProjectView(this, saver);
		select = new JComboBox();
		// select Action Listener
		select.addActionListener(event -> {
			String selectedProject = (String) select.getSelectedItem();
			int i = 0;
			while (!(selectedProject.equals(model.getProjects().get(i).getName()))) {
				i++;
			}

			this.projectView.setProjectModel(model.getProjects().get(i));
			this.columns.removeAll();
			for (int j = 0; j < this.projectView.getProjectModel().getColumns().size(); j++) {
				this.columns.add(this.projectView.getProjectModel().getColumns().get(j));
			}
			this.revalidate();
			this.repaint();
		});

		this.setLayout(new BorderLayout());

		logout = new JButton("Logout");
		createNew = new JButton("Create New");
		load = new JButton("Load");
		delete = new JButton("Delete");
		save = new JButton("Save");
		edit = new JButton("Edit");
		//edit button implemeted
		edit.addActionListener(event -> {
			this.projectView.editDialog();
		});
		// delete button implemented
		delete.addActionListener(l -> {
			String currentProject = (String) select.getSelectedItem();
			for(ProjectModel pm : model.getProjects()) {
				if(pm.getName().equals(currentProject)) {
					model.getProjects().remove(pm);
					select.removeItem(currentProject);

					this.remove(columns);

					this.revalidate();
					this.repaint();
					
					columns = new JPanel(new GridLayout());
					break;
				}
			}
			String selectedProject = (String) select.getItemAt(0);
			this.projectView.setProjectModel(model.getProjects().get(0));
			for (int j = 0; j < this.projectView.getProjectModel().getColumns().size(); j++) {
				System.out.println("Trying to add columns");
				this.columns.add(this.projectView.getProjectModel().getColumns().get(j));
			}
			this.add(columns);
			this.revalidate();
			this.repaint();
	});

		// save button implemented
		save.addActionListener(event -> {
			if (saver.getFlag()) {
				saver.setFileName(JOptionPane.showInputDialog(this, "Enter File Name"));
				System.out.println("SAVING");
				try {
					saver.readFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(saver.getFileName() + " contains the following projects: ");
				for (int i = 0; i < saver.getTaskBoard().getProjects().size(); i++) {
					System.out.println(saver.getTaskBoard().getProjects().get(i).getName());
				}

			} else {
				System.out.println(JOptionPane.showConfirmDialog(this, "No changes have been made."));
			}
		});

		// load button implemented
		load.addActionListener(event -> {
			saver.setFileName(
					JOptionPane.showInputDialog(this, "Enter the file name of the project you want to load."));
			System.out.println("Loading");
			try {
				saver.loadFile();
			} catch (ClassNotFoundException | IOException e1) {
				System.out.println("That is not a file name");
			}
			System.out.println("Attempting to display new taskBoardModel");
			this.model = saver.getTaskBoard();
			this.remove(this.getColumns());
			this.getColumns().removeAll();
			this.columns.add(model.getProjects().get(0).getColumns().get(0));
			this.add(columns);
			showButtons();

			for (int i = 0; i < model.getProjects().size(); i++) {
				System.out.println(model.getProjectTitles().get(i));
			}
			for (int i = 0; i < model.getProjects().size(); i++) {
				this.select.addItem(model.getProjectTitles().get(i));
				ProjectModel currentPModel = model.getProjects().get(i);
				for (int j = 0; j < model.getProjects().get(i).getColumns().size(); j++) {
					Column currentCol = currentPModel.getColumns().get(j);
					currentCol.getAddTaskButton().addActionListener(f -> {
						TaskView view = new TaskView(this, currentCol, currentPModel);
						view.display();
					});
				}
			}

			this.columns.revalidate();
			this.columns.repaint();
		});

		logout.addActionListener(event -> {
			// saving the taskBoardModel to the saver object
			if (saver.getFlag()) {
				saver.setFileName(JOptionPane.showInputDialog(this, "Enter File Name"));
				System.out.println("SAVING");
				try {
					saver.readFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(saver.getFileName() + " contains the following projects: ");
				for (int i = 0; i < saver.getTaskBoard().getProjects().size(); i++) {
					System.out.println(saver.getTaskBoard().getProjects().get(i).getName());
				}

			}
			JFrame newFrame = new JFrame();
			MainScreen newMain = new MainScreen(newFrame);
			// Automatically loading project
			if (!(this.saver.getFileName().equals("null"))) {
				System.out.println("trying automatic load");
				System.out.println(saver.getFileName());
				newMain.saver.setFileName(this.saver.getFileName());
				newMain.automaticLoad();
			}
			LoginView newLogin = new LoginView(newMain, newFrame);

			frame.remove(this);
			frame.dispose();

			Dimension loginD = new Dimension(800, 600);
			newFrame.setPreferredSize(loginD);

			newFrame.add(newLogin);
			newFrame.pack();
			newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			newFrame.setVisible(true);
		});

		JLabel project = new JLabel("Select Project");
		mainScreenButtons.add(project);
		mainScreenButtons.add(select);
		mainScreenButtons.add(load);
		mainScreenButtons.add(createNew);
		mainScreenButtons.add(logout);

		mainScreenButtons.setLayout(new GridLayout());

		this.add(mainScreenButtons, BorderLayout.PAGE_START);

		createNew.addActionListener(event -> update());
		/*
		 * edit.addActionListener(l -> { String currentProject = (String)
		 * select.getSelectedItem();
		 * 
		 * for(ProjectModel pm : model.getProjects()) {
		 * if(pm.getName().equals(currentProject)) { projectView.editColumn(pm); break;
		 * } }
		 * 
		 * });
		 */

		/*
		 * delete.addActionListener(l -> { String currentProject = (String)
		 * select.getSelectedItem(); for(ProjectModel pm : model.getProjects()) {
		 * if(pm.getName().equals(currentProject)) { model.getProjects().remove(pm);
		 * select.removeItem(currentProject); this.remove(projectView);
		 * //columns.removeAll(); this.revalidate(); this.repaint(); break; } } });
		 */

	}

	// called if fileName is is saver
	public void automaticLoad() {
		System.out.println("automatic Loading");
		try {
			saver.loadFile();
		} catch (ClassNotFoundException | IOException e1) {
			System.out.println("That is not a file name");
		}
		System.out.println("Attempting to display new taskBoardModel");
		this.model = saver.getTaskBoard();
		this.remove(this.getColumns());
		this.getColumns().removeAll();
		this.columns.add(model.getProjects().get(0).getColumns().get(0));
		this.add(columns);
		showButtons();

		for (int i = 0; i < model.getProjects().size(); i++) {
			System.out.println(model.getProjectTitles().get(i));
		}
		for (int i = 0; i < model.getProjects().size(); i++) {
			this.select.addItem(model.getProjectTitles().get(i));
			ProjectModel currentPModel = model.getProjects().get(i);
			for (int j = 0; j < model.getProjects().get(i).getColumns().size(); j++) {
				Column currentCol = currentPModel.getColumns().get(j);
				currentCol.getAddTaskButton().addActionListener(f -> {
					TaskView view = new TaskView(this, currentCol, currentPModel);
					view.display();
				});
				
			}
		}
		
		this.columns.revalidate();
		this.columns.repaint();
	}

	public void showButtons() {
		mainScreenButtons.add(edit);
		mainScreenButtons.add(save);
		mainScreenButtons.add(delete);
	}

	public ProjectView getProjectView() {
		return projectView;
	}

	public void update() {
		projectView = new ProjectView(this, saver);
		projectView.displayDialog();
	}

	public JPanel getColumns() {
		return columns;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public TaskBoardModel getModel() {
		return model;
	}

	public void addProject(ProjectModel pm) {
		model.addProject(pm);
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		Dimension loginD = new Dimension(800, 600);
		frame.setPreferredSize(loginD);
		MainScreen mainscreen = new MainScreen(frame);
		LoginView loginScreen = new LoginView(mainscreen, frame);

		frame.add(loginScreen);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
