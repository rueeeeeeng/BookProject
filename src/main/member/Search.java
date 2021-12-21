package main.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import db.DB;

public class Search extends JFrame implements ActionListener {
	private JButton btnSearch;
	private JButton btnCancel;
	private DefaultTableModel tableModel1;
	private JTextField tf;

	public Search() {
		setTitle("회원검색");
		setSize(350, 250);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tableModel1 = MemberList.getTableModel();
		// 레이아웃
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		JLabel lblSearch = new JLabel("회원검색");
		lblSearch.setForeground(Color.WHITE);
		p1.add(lblSearch);

		JPanel p2 = new JPanel();
		JLabel lblTitle = new JLabel("회원주민번호");
		p2.add(lblTitle, BorderLayout.NORTH);

		tf = new JTextField(12);
		p2.add(tf, BorderLayout.NORTH);

		JPanel p3 = new JPanel();
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		p3.add(btnSearch, BorderLayout.SOUTH);
		p3.add(btnCancel, BorderLayout.SOUTH);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);

	}

	public static void main(String[] args) {
		db.DB.init();
		new Search();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnSearch) {
			searchMember();

		} else if (obj == btnCancel) {
			dispose();
		}
	}

	public void searchMember() {
		String mb_num = tf.getText();
		boolean check = checkNum(mb_num);
		if (tf.getText().equals("")) { // 검색어를 입력하지 않았을 때
			JOptionPane.showMessageDialog(null, "주민번호를 입력해주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(check == true){ // 검색어를 입력했을때
			for (int i = 0; i < tableModel1.getRowCount();) {
				tableModel1.removeRow(i);
			}
			String sql = "SELECT * FROM MEMBER WHERE MB_NUM = '" + mb_num + "'"; 			
			ResultSet rs = DB.getResultSet(sql);
			try {
				while (rs.next()) {
					String name = rs.getString("MB_NAME");
					String num = rs.getString("MB_NUM");
					String phone = rs.getString("MB_PHONE");
					String addr = rs.getString("MB_ADDR");
					
					Object data[] = {name, num, phone, addr};
					tableModel1.addRow(data);
				}
				JOptionPane.showMessageDialog(null, "검색이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE); // 버튼 확인으로
																												// 바꾸기
			} catch (SQLException e) {
				e.printStackTrace();
			}
			} else {
				JOptionPane.showMessageDialog(null, "입력하신 회원의 정보는 존재하지 않습니다!");
			}


			tf.setText("");

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
