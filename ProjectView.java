import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class ProjectView extends JPanel {

	private ProjectModel model;
	private FileSave saver; // FileSave instance variable
	private JButton cancel;
	private JButton plus;
	private JButton minus;
	private JButton upKey;
	private JButton downKey;
	private JButton createButton;
	private JFrame dialogFrame;
	private MainScreen screen;
	public ArrayList<JTextField> textFields;
	private JTextField nameBox;
	private JDialog dialog;

	// filesave added to ctor
	public ProjectView(MainScreen screen, FileSave saver) {
		this.saver = saver;
		this.setLayout(new BorderLayout());
		this.screen = screen;
		nameBox = new JTextField("Enter Project Name Here");
	}

	public ProjectModel getProjectModel() {
		return model;
	}

	public void setProjectModel(ProjectModel model) {
		this.model = model;
	}

	public void displayTopDialog() {

		dialogFrame = new JFrame();
		Dimension loginD = new Dimension(800, 600);
		dialogFrame.setPreferredSize(loginD);

		dialog = new JDialog(dialogFrame, "Create New Project");
		dialog.setBounds(70, 70, 600, 500);
		dialog.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		JLabel name = new JLabel("Name");
		// JTextField nameBox = new JTextField("Enter Name of Project Here");
		nameBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				nameBox.setText("");
			}
		});

		p1.add(name);
		p1.add(nameBox);
		dialog.add(p1, BorderLayout.NORTH);
	}

	public void displayDialog() {
		displayTopDialog();
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		JLabel columns = new JLabel("Columns");
		plus = new JButton("+");
		p2.add(columns);
		p2.add(plus);

		textFields = new ArrayList<>();

		plus.addActionListener(event -> {

			if (textFields.size() < 4) {
				upKey = new JButton("^");
				downKey = new JButton("v");
				JPanel p5 = new JPanel();

				JTextField f1 = new JTextField("Enter Column Name");
				f1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						f1.setText("");
					}
				});
				JButton minus = new JButton("-");
				textFields.add(f1);

				minus.addActionListener(deleteLine -> {
					textFields.remove(f1);
					p2.remove(p5);

					p2.revalidate();
					p2.repaint();
				});

				if (textFields.size() > 1) {
					int upIndex = textFields.indexOf(f1);
					if (upIndex > 0) {
						upKey.addActionListener(e -> {
							String textName = textFields.get(upIndex).getText();
							String secondName = textFields.get(upIndex - 1).getText();

							textFields.get(upIndex).setText(secondName);
							textFields.get(upIndex - 1).setText(textName);
						});
					}
				}
				downKey.addActionListener(l -> {
					int downIndex = textFields.indexOf(f1);
					System.out.println(downIndex);
					if (downIndex < textFields.size() - 1) {
						String textName = textFields.get(downIndex).getText();
						String secondName = textFields.get(downIndex + 1).getText();

						textFields.get(downIndex).setText(secondName);
						textFields.get(downIndex + 1).setText(textName);
					}

				});

				p5.add(f1);
				p5.add(minus);
				p5.add(upKey);
				p5.add(downKey);

				p2.add(p5, BorderLayout.WEST);
				p2.revalidate();
				p2.repaint();

			}

		});

		dialog.add(p2, BorderLayout.WEST);

		JPanel p3 = new JPanel();
		createButton = new JButton("Create");
		createButton.addActionListener(event -> {
			System.out.println("Saver flag set to true");
			saver.setFlag(true); // flag set true because creating new project
			screen.remove(screen.getColumns());
			screen.getColumns().removeAll();

			model = new ProjectModel();
			screen.addProject(model);
			model.setName(nameBox.getText());

			for (int i = 0; i < textFields.size(); i++) {
				String s = textFields.get(i).getText();
				model.addColumnName(s);
				Column column = new Column(s);
				model.addColumn(column);
				column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
				column.setBackground(Color.WHITE);
				column.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				column.getAddTaskButton().addActionListener(e -> {
					TaskView view = new TaskView(screen, column, model);
					view.display();
				});
				screen.getColumns().add(column);
			}
			screen.add(screen.getColumns());

			screen.revalidate();
			screen.repaint();
			screen.select.addItem(model.getName());
			screen.getModel().getProjectTitles().add(model.getName()); // names inserted here
			screen.select.setSelectedItem(model.getName());
			screen.showButtons();
			dialogFrame.setVisible(false);
			dialogFrame.dispose();

		});

		cancel = new JButton("Cancel");
		cancel.addActionListener(event -> {

			dialogFrame.setVisible(false);

			dialogFrame.dispose();

		});
		p3.add(createButton);
		p3.add(cancel);
		dialog.add(p3, BorderLayout.SOUTH);
		dialog.setVisible(true);

	}

	public void editDialog() {
		dialogFrame = new JFrame();
		Dimension loginD = new Dimension(800, 600);
		dialogFrame.setPreferredSize(loginD);

		dialog = new JDialog(dialogFrame, "edit project: " + this.getProjectModel().getName());
		dialog.setBounds(70, 70, 600, 500);
		dialog.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		JLabel name = new JLabel("Name");
		// JTextField nameBox = new JTextField("Enter Name of Project Here");
		nameBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				nameBox.setText("");
			}
		});

		p1.add(name);
		p1.add(nameBox);
		dialog.add(p1, BorderLayout.NORTH);
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		JLabel columns = new JLabel("Columns");
		plus = new JButton("+");
		p2.add(columns);
		p2.add(plus);
		
		
		// textFields = new ArrayList<>();
		for (int i = 0; i < this.getProjectModel().getColumnNames().size(); i++) {
			JPanel p5 = new JPanel();
			JTextField f1 = textFields.get(i);
			f1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					f1.setText("");
				}
			});
			
			JButton minus = new JButton("-");
			minus.addActionListener(event -> {
				textFields.remove(f1);
				p2.remove(p5);
				p2.revalidate();
				p2.repaint();
			});
			
			upKey = new JButton("^");
			downKey = new JButton("v");
			if (textFields.size() > 1) {
				int upIndex = textFields.indexOf(f1);
				System.out.println(textFields.get(upIndex).getText() + " upIndex is " + upIndex);
				if (upIndex > 0) {
					upKey.addActionListener(e -> {
						String textName = textFields.get(upIndex).getText();
						String secondName = textFields.get(upIndex - 1).getText();

						textFields.get(upIndex).setText(secondName);
						textFields.get(upIndex - 1).setText(textName);
					});
				}
			}
			downKey.addActionListener(l -> {
				int downIndex = textFields.indexOf(f1);
				System.out.println(textFields.get(downIndex).getText() + " downIndex is " + downIndex);
				if (downIndex < textFields.size() - 1) {
					String textName = textFields.get(downIndex).getText();
					String secondName = textFields.get(downIndex + 1).getText();

					textFields.get(downIndex).setText(secondName);
					textFields.get(downIndex + 1).setText(textName);
				}

			});
			
			p5.add(f1);
			p5.add(minus);
			p5.add(upKey);
			p5.add(downKey);
			p2.add(p5, BorderLayout.WEST);
			p2.revalidate();
			p2.repaint();
		}
		for(int i = 0; i < textFields.size(); i++) {
			System.out.println("TextFields index " + i + " has " + textFields.get(i).getText());
		}

		plus.addActionListener(event -> {

			if (textFields.size() < 4) {
				upKey = new JButton("^");
				downKey = new JButton("v");
				JPanel p6 = new JPanel();

				JTextField f1 = new JTextField("Enter Column name");

				f1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						f1.setText("");
					}
				});
				JButton minus = new JButton("-");
				textFields.add(f1);

				minus.addActionListener(deleteLine -> {
					textFields.remove(f1);
					p2.remove(p6);

					p2.revalidate();
					p2.repaint();
				});

				if (textFields.size() > 1) {
					int upIndex = textFields.indexOf(f1);
					if (upIndex > 0) {
						upKey.addActionListener(e -> {
							String textName = textFields.get(upIndex).getText();
							String secondName = textFields.get(upIndex - 1).getText();

							textFields.get(upIndex).setText(secondName);
							textFields.get(upIndex - 1).setText(textName);
						});
					}
				}
				downKey.addActionListener(l -> {
					int downIndex = textFields.indexOf(f1);
					System.out.println(downIndex);
					if (downIndex < textFields.size() - 1) {
						String textName = textFields.get(downIndex).getText();
						String secondName = textFields.get(downIndex + 1).getText();

						textFields.get(downIndex).setText(secondName);
						textFields.get(downIndex + 1).setText(textName);
					}

				});

				p6.add(f1);
				p6.add(minus);
				p6.add(upKey);
				p6.add(downKey);

				p2.add(p6, BorderLayout.WEST);
				p2.revalidate();
				p2.repaint();

			}

		});

		dialog.add(p2, BorderLayout.WEST);

		JPanel p3 = new JPanel();
		createButton = new JButton("Save Changes");
		createButton.addActionListener(event -> {
			screen.getColumns().removeAll();
			System.out.println("Saver flag set to true");
			saver.setFlag(true); // flag set true because creating new project
			screen.remove(screen.getColumns());
			screen.getColumns().removeAll();
			//model = new ProjectModel();
			//screen.addProject(model);
			model.setName(nameBox.getText());
			
			for (int i = 0; i < textFields.size(); i++) {
				String s = textFields.get(i).getText();
				System.out.println(s);
				//model.addColumnName(s);
				Column column = new Column(s);
				//model.addColumn(column);
				column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
				column.setBackground(Color.WHITE);
				column.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				column.getAddTaskButton().addActionListener(e -> {
					TaskView view = new TaskView(screen, column, model);
					view.display();
				});
				screen.getColumns().add(column);
			}
			//screen.add(screen.getColumns());

			screen.revalidate();
			screen.repaint();
			//screen.select.addItem(model.getName());
			//screen.getModel().getProjectTitles().add(model.getName()); // names inserted here
			//screen.select.setSelectedItem(model.getName());
			//screen.showButtons();
			dialogFrame.setVisible(false);
			dialogFrame.dispose();
			
		});
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(event -> {

			dialogFrame.setVisible(false);

			dialogFrame.dispose();

		});
		p3.add(createButton);
		p3.add(cancel);
		dialog.add(p3, BorderLayout.SOUTH);
		dialog.setVisible(true);

	}

	public void update() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
