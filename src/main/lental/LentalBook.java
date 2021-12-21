package main.lental;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.DB;


public class LentalBook extends JFrame implements ActionListener{
	private JPanel p1, p2, p3;
	private JButton btnBack;
	private String[] name = {"대여번호", "회원이름", "회원전화", "도서이름", "도서번호", "날짜" };
	private DefaultTableModel m2 = new DefaultTableModel(name, 0);
	private JTable table1;
	private JScrollPane sp;
	public LentalBook(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);
		
		setLayout(new BorderLayout());
		p1 = new JPanel();
		add(p1, BorderLayout.NORTH);
		btnBack = new JButton("돌아가기");
		p1.add(btnBack);
		btnBack.addActionListener(this);
		
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);
		
		LentalInfo();
		
		p3 = new JPanel();
		add(p3, BorderLayout.SOUTH);
		
		
		setVisible(true);
	}

	private void LentalInfo() {
		table1 = new JTable(m2);
		
		sp = new JScrollPane(table1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p2.add(sp);
		
		String sql1 = "SELECT * FROM RENT";
		ResultSet rs = DB.getResultSet(sql1);
		
		try {
			while(rs.next()) {
				int num = rs.getInt("RENT_NO");
				String name = rs.getString("MEM_NAME");
				String phone = rs.getString("MEM_PHONE");
				String code = rs.getString("LIB_CODE");
				String lName = rs.getString("LIB_NAME");
				Date date = rs.getDate("RENT_DATE");
				
				Object[] data = {num, name, phone, lName, code, date};
				m2.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DB.init();
		new LentalBook("대여정보", 500, 450);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnBack) {
			dispose();
		}
		
	}

}
