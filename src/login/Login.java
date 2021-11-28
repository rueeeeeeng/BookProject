package login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Login extends JFrame implements ActionListener, KeyListener {

	private ImageIcon icon;
	private JPanel loginPanel, mainPanel;
	private JLabel image;
	private JButton btnLogin, btnExit;
	private JTextField tfID, tfPW;

	public Login(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
//		setUndecorated(true); //타이틀바 날리기

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
		tfID.addKeyListener(this);
		tfPW = new JPasswordField(10);
		tfPW.addKeyListener(this);

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
			login();
		} else if (obj == btnExit) {
			System.exit(0);
		}
	}

	private void login() { // 로그인 메소드
		String id = tfID.getText();
		String pw = tfPW.getText();

		String sql = "SELECT * FROM ADMIN WHERE ID = '" + id + "' AND PW = '" + pw + "'";
		ResultSet rs = db.DB.getResultSet(sql);
		boolean check = check(rs); // db에 입력한 id와 pw가 있는지 확인
		if (tfID.getText().equals("") || tfPW.getText().equals("")) {
			JOptionPane.showInternalMessageDialog(null, "입력이 올바르지 않습니다.");
			tfID.setText("");
			tfPW.setText("");
			tfID.requestFocus();
		} else {
			if (check) { // id와 pw가 있다면 로그인 성공, main 프레임 열림
				db.DB.init();
				new main.Main("도서 관리 프로그램", 950, 700);
				dispose(); // main 프레임이 열리면 로그인 창은 닫힘
			} else { // 로그인 실패
				JOptionPane.showInternalMessageDialog(null, "해당 사용자가 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				tfID.setText("");
				tfPW.setText("");
				tfID.requestFocus();
			}
		}
	}

	/**
	 * id,pw체크 메서드
	 * 
	 * @param rs id, pw 가져오는 db쿼리
	 * @return 정보가 있으면 true, 없으면 false
	 */
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) { // 엔터키 이벤트
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			login();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
