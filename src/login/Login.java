package login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Main;

public class Login extends JFrame implements ActionListener {

	private ImageIcon icon;
	private JPanel loginPanel, mainPanel;
	private JLabel image;
	private JButton btnLogin, btnExit;
	private JTextField tfID, tfPW;
	private boolean inputCheck;

	public Login(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		icon = new ImageIcon("Images/Intro.jpg");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(1000, 450, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);

		setLayout(new BorderLayout());
		image = new JLabel(changeIcon);

		mainPanel = new JPanel();
		loginPanel = new JPanel(new FlowLayout());

		JLabel id = new JLabel("ID: ");
		JLabel pw = new JLabel("PW: ");
		tfID = new JTextField(10);
		tfPW = new JPasswordField(10);

		btnLogin = new JButton("로그인");
		btnLogin.addActionListener(this);
		btnExit = new JButton("종료");
		btnExit.addActionListener(this);

		loginPanel.add(id);
		loginPanel.add(tfID);
		loginPanel.add(pw);
		loginPanel.add(tfPW);
		loginPanel.add(btnLogin);
		loginPanel.add(btnExit);
		add(mainPanel, BorderLayout.NORTH);
		mainPanel.add(image);
		add(loginPanel);
		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB.init();
		new Login("관리자 로그인", 1020, 550);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnLogin) {
			String id = tfID.getText();
			String pw = tfPW.getText();

			String sql = "SELECT * FROM ADMIN WHERE ID = '" + id + "' AND PW = '" + pw + "'";
			ResultSet rs = db.DB.getResultSet(sql);
			boolean check = check(rs);
			if (check) {
				db.DB.init();
				new main.Main("도서 관리 프로그램", 950, 700);
			} else {
				JOptionPane.showInternalMessageDialog(null, "해당 사용자가 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				tfID.setText("");
				tfPW.setText("");
				tfID.requestFocus();
			}
		} else if (obj == btnExit) {
			System.exit(0);
		}

	}

	private boolean check(ResultSet rs) {
		boolean check = false;
		try {
			if (rs.next()) {
				check = true;
			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}

}
