import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

	private TaskBoardModel taskBoardModel; 
	private MainScreen mainscreen;
	private JFrame frame;
	private JTextField userText;
	private JTextField passwordText;

	public LoginView(MainScreen mainScreen, JFrame frame) {
		this.mainscreen = mainScreen;
		this.frame = frame;
		this.taskBoardModel = mainscreen.getModel();
		
		this.setLayout(null);
		JLabel titleLabel = new JLabel("Task Board Login");
		titleLabel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		titleLabel.setBounds(300, 10, 300, 250);
		add(titleLabel);

		JLabel userLabel = new JLabel("Username");
		userLabel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		userLabel.setBounds(250, 100, 300, 250);
		add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(350, 213, 160, 25);
		add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		passwordLabel.setBounds(250, 170, 300, 250);
		add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 283, 160, 25);
		add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setFont(new Font("Arial", Font.TRUETYPE_FONT, 15));
		loginButton.setBounds(340, 353, 150, 40);
		add(loginButton);
		
		loginButton.addActionListener(event -> update());
	}
	
	public void update() {
		String username = userText.getText();
		String password = passwordText.getText();
		if(taskBoardModel.checkLogin(username,password)) {
			this.setVisible(false);
			if(!this.isVisible()) {
				frame.add(mainscreen);
				mainscreen.setVisible(true);
				frame.revalidate();
			}
		} else {
			JOptionPane.showMessageDialog(this,"Error: Incorrect Username or Password");
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}