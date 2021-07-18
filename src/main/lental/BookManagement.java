package main.lental;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import db.DB;

public class BookManagement extends JFrame implements ActionListener, KeyListener{
	private JPanel p1, p2, p3, p4, p5, p6;
	private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
	private JButton btnSearch, btnAdmin, btnSet, btnRe, btnBack;
	private String[] n1 = {"도서번호", "제목", "저자", "출판사", "가격", "대출여부" };
	private DefaultTableModel m = new DefaultTableModel(n1, 0);
	private JTable table;
	private JScrollPane sp;
	private Library nLi;
	public BookManagement(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);
		
		p1 = new JPanel();
		
		p3 = new JPanel();
		
		p1.setLayout(new BorderLayout());
		p1.add(p3, BorderLayout.NORTH);
		
		lb1 = new JLabel("도서명");
		tf1 = new JTextField(10);
		btnSearch = new JButton("검색");
		p3.add(lb1);
		p3.add(tf1);
		p3.add(btnSearch);
		btnSearch.addActionListener(this);
		
		p4 = new JPanel();
		p1.add(p4, BorderLayout.CENTER);
		p4.setLayout(new GridLayout(3, 4, 10, 10));
		p4.setBorder(BorderFactory.createEmptyBorder(10 , 70, 10 , 70));
		

		
		lb2 = new JLabel("도서번호");
		tf2 = new JTextField(10);
		
		lb3 = new JLabel("제 목");
		tf3 = new JTextField(10);
		
		lb4 = new JLabel("저 자");
		tf4 = new JTextField(10);
		
		lb5 = new JLabel("출판사");
		tf5 = new JTextField(10);
		
		lb6 = new JLabel("가 격");
		tf6 = new JTextField(10);
		
		lb7 = new JLabel("대출여부");
		tf7 = new JTextField(10);
		
		p4.add(lb2);
		p4.add(tf2);
		p4.add(lb3);
		p4.add(tf3);
		p4.add(lb4);
		p4.add(tf4);
		p4.add(lb5);
		p4.add(tf5);
		p4.add(lb6);
		p4.add(tf6);
		p4.add(lb7);
		p4.add(tf7);
		
		p2 = new JPanel();
		
		p2.setLayout(new BorderLayout());
		
		p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.add(p5, BorderLayout.NORTH);
		
		btnAdmin = new JButton("대여/반납");
		btnSet = new JButton("초기화");
		btnRe = new JButton("새로고침");
		btnBack = new JButton("돌아가기");
		
		p5.add(btnAdmin);
		p5.add(btnSet);
		p5.add(btnRe);
		p5.add(btnBack);
		
		btnAdmin.addActionListener(this);
		btnSet.addActionListener(this);
		btnRe.addActionListener(this);
		btnBack.addActionListener(this);
		
		List();
		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		
		tf1.addKeyListener(this);
		
		setVisible(true);
		
	}
	private void List() {
		p6 = new JPanel();
		p2.add(p6, BorderLayout.CENTER);
		table = new JTable(m);
		
		sp = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p6.add(sp);
		String y = "Y";
		
		String sql = "SELECT * FROM LIB WHERE LIB_STATE LIKE '" + y + "'";
		ResultSet rs3 = DB.getResultSet(sql);
		try {
			while(rs3.next()) {
				String code = rs3.getString("LIB_CODE");
				String name = rs3.getString("LIB_NAME");
				String author = rs3.getString("LIB_AUTHOR");
				String publisher = rs3.getString("LIB_PUBLISHER");
				int price = rs3.getInt("LIB_PRICE");
				String state = rs3.getString("LIB_STATE");
				
				Object[] data = {code, name, author, publisher, price, state};
				m.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DB.init();
		new BookManagement("도서대여/반납", 600, 650);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnSearch) {
			 //for (int i = 0; i < table.getRowCount();) {
		            //m.removeRow(i);
		         //}
			String s = tf1.getText();
	         String sql = "SELECT * FROM LIB WHERE LIB_NAME LIKE '" + s + "%'";
	         ResultSet rs4 = DB.getResultSet(sql);
	         try {
	            while (rs4.next()) {
	            	String code = rs4.getString("LIB_CODE");
	            	String name = rs4.getString("LIB_NAME");
	            	String author = rs4.getString("LIB_AUTHOR");
	            	String publisher = rs4.getString("LIB_PUBLISHER");
	            	int price = rs4.getInt("LIB_PRICE");
	            	String state = rs4.getString("LIB_STATE");
	            	tf2.setText(code);
	            	tf3.setText(name);
	            	tf4.setText(author);
	            	tf5.setText(publisher);
	            	tf6.setText(Integer.toString(price));
	            	tf7.setText(state);
	               Object data[] = {code, name, author, publisher, price, state};
	               m.addRow(data);
	            }
	         } catch (SQLException e1) {
	            e1.printStackTrace();
	         }
			
		} if(obj == btnAdmin) {
			nLi = new Library("대여/반납", 480, 500, this);
		} if(obj == btnSet) {
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			tf5.setText("");
			tf6.setText("");
			tf7.setText("");
	}
		if(obj == btnRe) {
			for (int i = 0; i < table.getRowCount();) {
	            m.removeRow(i);
	         }
			String sql3 = "SELECT * FROM LIB";
			ResultSet rs5 = DB.getResultSet(sql3);
			try {
				while(rs5.next()) {
					String code = rs5.getString("LIB_CODE");
					String name = rs5.getString("LIB_NAME");
					String author = rs5.getString("LIB_AUTHOR");
					String publisher = rs5.getString("LIB_PUBLISHER");
					int price = rs5.getInt("LIB_PRICE");
					String state = rs5.getString("LIB_STATE");
					
					Object[] data = {code, name, author, publisher, price, state};
					m.addRow(data);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		if(obj == btnBack) {
			dispose();
		}

}


	public JTextField getTf1() {
		return tf1;
	}
	public JTextField getTf2() {
		return tf2;
	}
	public JTextField getTf3() {
		return tf3;
	}

	public JTextField getTf4() {
		return tf4;
	}

	public JTextField getTf5() {
		return tf5;
	}

	public JTextField getTf6() {
		return tf6;
	}

	public JTextField getTf7() {
		return tf7;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			String s = tf1.getText();
	         String sql = "SELECT * FROM LIB WHERE LIB_NAME LIKE '" + s + "%'";
	         ResultSet rs4 = DB.getResultSet(sql);
	         try {
	            while (rs4.next()) {
	            	String code = rs4.getString("LIB_CODE");
	            	String name = rs4.getString("LIB_NAME");
	            	String author = rs4.getString("LIB_AUTHOR");
	            	String publisher = rs4.getString("LIB_PUBLISHER");
	            	int price = rs4.getInt("LIB_PRICE");
	            	String state = rs4.getString("LIB_STATE");
	            	tf2.setText(code);
	            	tf3.setText(name);
	            	tf4.setText(author);
	            	tf5.setText(publisher);
	            	tf6.setText(Integer.toString(price));
	            	tf7.setText(state);
	               Object data[] = {code, name, author, publisher, price, state};
	               m.addRow(data);
	            }
	         } catch (SQLException e1) {
	            e1.printStackTrace();
	         }
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
