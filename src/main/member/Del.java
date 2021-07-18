package main.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DB;

public class Del extends JFrame implements ActionListener{
	private JPanel p1, p2, p3;
	private JTextField tf1;
	private JButton b1, b2;
	public Del() {
		setTitle("회원삭제");
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		p1.setBackground(Color.DARK_GRAY);
		
		JLabel lb1 = new JLabel("회원삭제");
		p1.add(lb1);
		lb1.setForeground(Color.WHITE);
		
		JLabel lb2 = new JLabel("회원주민번호 : ");
		tf1 = new JTextField(15);
		
		p2.add(lb2);
		p2.add(tf1);
		
		b1 = new JButton("삭제");
		b2 = new JButton("취소");
		
		p3.add(b1);
		p3.add(b2);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new Del();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String num = tf1.getText();
		if(obj == b1) {
			String sql2 = "SELECT * FROM MEMBER";
			ResultSet rs = DB.getResultSet(sql2);
			try {
				while(rs.next()) {
					
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			boolean check = checkNum(num);
			if(check == true) {
				String sql = "DELETE FROM MEMBER WHERE MB_NUM = '" + num + "'";
				DB.executeQuery(sql);
				JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");
				tf1.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "해당하는 회원의 정보가 없습니다!");
				tf1.setText("");
			}
		} else if(obj == b2) {
			dispose();
		}
	}

	private boolean checkNum(String num) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE MB_NUM = '" + num + "'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
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
