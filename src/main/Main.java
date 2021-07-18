package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Member;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.book.Management;
import main.lental.BookManagement;
import main.lental.LentalBook;

public class Main extends JFrame implements ActionListener {
	ImageIcon icon;
	private JPanel p1;
	private JLabel welcomeLabel;
	private JButton btnMember, btnBook, btnlental, btnBookInfo, btnExit;

	public Main(String title, int width, int height) {
		icon = new ImageIcon("Images/Welcome.jpg");
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		add(p1, BorderLayout.NORTH);

		btnMember = new JButton("회원 등록/삭제");
		btnMember.setBackground(Color.WHITE);
		btnMember.addActionListener(this);

		btnBook = new JButton("도서 등록/삭제");
		btnBook.setBackground(Color.WHITE);
		btnBook.addActionListener(this);

		btnlental = new JButton("도서대여/반납");
		btnlental.setBackground(Color.WHITE);
		btnlental.addActionListener(this);

		btnBookInfo = new JButton("모든대여정보");
		btnBookInfo.setBackground(Color.WHITE);
		btnBookInfo.addActionListener(this);

		btnExit = new JButton("종료");
		btnExit.setBackground(Color.WHITE);
		btnExit.addActionListener(this);

		p1.add(btnMember);
		p1.add(btnBook);
		p1.add(btnlental);
		p1.add(btnBookInfo);
		p1.add(btnExit);
		ImageIcon icon = new ImageIcon("Images/Welcome.jpg");
		welcomeLabel = new JLabel(icon);
		add(welcomeLabel);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Main("도서 관리 프로그램", 950, 700);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnMember) {
//			dispose();
			this.setVisible(false);

		} else if (obj == btnBook) {
			db.DB.init();
			new main.book.Management();
		} else if (obj == btnlental) {
			db.DB.init();
			new main.lental.BookManagement("도서대여/반납", 600, 650);
		} else if (obj == btnBookInfo) {
			new main.lental.LentalBook("대여정보", 500, 450);
		} else if (obj == btnExit) {
			System.exit(0);
		}
	}
}