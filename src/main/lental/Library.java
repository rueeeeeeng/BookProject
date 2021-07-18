package main.lental;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

import db.DB;

public class Library extends JFrame implements ActionListener{
	private JPanel p1, p2, p3, p4;
	private JLabel lb1 , lb2, lb3;
	private JTextField tf1, tf2, tf3;
	private JButton btnBorrow, btnRe, btnCancel;
	private String[] non = {"대여번호", "회원이름", "회원전화", "도서이름", "도서번호", "날짜" };
	private DefaultTableModel m1 = new DefaultTableModel(non, 0);
	private JTable table1;
	private BookManagement bookManagement;
	private JScrollPane sp;
	private String state, bName, bCode, reName, rePhone, reCode;
	private int num, count;
	private String n;
	public Library(String title, int width, int height, BookManagement bookManagement) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		setResizable(false);
		setLayout(new BorderLayout());
		
		this.bookManagement = bookManagement;
		
		p1 = new JPanel();
		add(p1, BorderLayout.NORTH);
		
		p1.setLayout(new BorderLayout());
		p3 = new JPanel();
		p1.add(p3, BorderLayout.NORTH);
		
		lb1 = new JLabel("회원주민번호");
		tf1 = new JTextField(10);
		btnBorrow = new JButton("대여");
		btnRe = new JButton("반납");
		btnCancel = new JButton("취소");
		
		p3.add(lb1);
		p3.add(tf1);
		p3.add(btnBorrow);
		p3.add(btnRe);
		p3.add(btnCancel);
		
		btnBorrow.addActionListener(this);
		btnRe.addActionListener(this);
		btnCancel.addActionListener(this);
		
		p4 = new JPanel();
		p1.add(p4,BorderLayout.CENTER);
		
		lb2 = new JLabel("도서번호");
		tf2 = new JTextField(10);
		lb3 = new JLabel("제목");
		tf3 = new JTextField(10);
		
		p4.add(lb2);
		p4.add(tf2);
		p4.add(lb3);
		p4.add(tf3);
		
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);
		
		table1 = new JTable(m1);
		sp = new JScrollPane(table1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p2.add(sp);
		String sql = "SELECT * FROM RENT";
		ResultSet rs = DB.getResultSet(sql);
		try {
			while(rs.next()) {
				int no = rs.getInt("RENT_NO");
				String name = rs.getString("MEM_NAME");
				String phone = rs.getString("MEM_PHONE");
				String code = rs.getString("LIB_CODE");
				String bName = rs.getString("LIB_NAME");
				Date date = rs.getDate("RENT_DATE");
				
				Object[] data = {no, name, phone, bName, code, date};
				m1.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}



	public static void main(String[] args) {
		DB.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnBorrow) {
			if(!tf1.getText().equals("")) {
				n = tf1.getText();
				String sql = "SELECT MB_NAME, MB_PHONE FROM MEMBER WHERE MB_NUM LIKE '" + n + "'";
				String c = bookManagement.getTf2().getText();
				String sql4 = "SELECT LIB_STATE FROM LIB WHERE LIB_CODE = '" + c + "'";
				ResultSet rs2 = DB.getResultSet(sql4);
				try {
					while(rs2.next()) {
						state = rs2.getString("LIB_STATE");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				if(state.equals("Y")) {
				Count();
				String sql2 = "SELECT MB_NAME, MB_PHONE FROM MEMBER WHERE MB_NUM LIKE '" + n + "'";
				ResultSet rs = DB.getResultSet(sql2);
				try {
					while(rs.next()) {
						num = count+1;
						String mName = rs.getString("MB_NAME");
						reName = mName;
						String mPhone = rs.getString("MB_PHONE");
						rePhone = mPhone;
						bName = bookManagement.getTf3().getText();
						bCode = bookManagement.getTf2().getText();
						
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
						Calendar time = Calendar.getInstance();
						
						String date = sd.format(time.getTime());
					
						Object[] data = {num, mName, mPhone, bName, bCode, date};
						
						tf2.setText(bookManagement.getTf2().getText());
						tf3.setText(bookManagement.getTf3().getText());
						
						m1.addRow(data);
						
						
					}
					
					String sql3 = "UPDATE LIB SET LIB_STATE = 'N' WHERE LIB_CODE = '" + bCode + "'";
					DB.executeQuery(sql3);
					
					String sql5 = "INSERT INTO RENT VALUES" + "('" + num + "','" + reName + "','" + rePhone + "','" + bCode + 
							"','" + bName + "', TO_DATE(SYSDATE, 'YY-MM-DD'))";
					
					DB.executeQuery(sql5);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
				else if(state.equals("N")) {
					JOptionPane.showMessageDialog(null, "대여중인 도서입니다.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
				}
			}else if(tf1.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "주민번호를 입력하세요.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
			}

		}
		if(obj == btnRe) {
			String n = tf1.getText();
			if(!n.equals("")) {
				String c = bookManagement.getTf2().getText();
				String sql2 = "SELECT LIB_STATE FROM LIB WHERE LIB_CODE = '" + c + "'";
				ResultSet rs2 = DB.getResultSet(sql2);
				try {
					while(rs2.next()) {
						state = rs2.getString("LIB_STATE");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				} if(state.equals("N")) {
				tf2.setText(bookManagement.getTf2().getText());
				tf3.setText(bookManagement.getTf3().getText());
				reCode = bookManagement.getTf2().getText();
				for (int i = 0; i < table1.getRowCount();) {
		            m1.removeRow(i);
		         }
				String sql3 = "DELETE FROM RENT WHERE LIB_CODE = '" + reCode + "'";
				DB.executeQuery(sql3);
				String sql = "SELECT * FROM RENT";
				ResultSet rs = DB.getResultSet(sql);
				try {
					while(rs.next()) {
						int num = rs.getInt("RENT_NO");
						String name = rs.getString("MEM_NAME");
						String phone = rs.getString("MEM_PHONE");
						String code = rs.getString("LIB_CODE");
						String lName = rs.getString("LIB_NAME");
						Date date = rs.getDate("RENT_DATE");
						
						Object[] data = {num, name, phone, lName, code, date};
						m1.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String sql1 = "UPDATE LIB SET LIB_STATE = 'Y' WHERE LIB_CODE = '" + reCode + "'";
				DB.executeQuery(sql1);
			} 
				else if(state.equals("Y")) {
					JOptionPane.showMessageDialog(null, "이미 반납되었습니다.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
			}
			} else if(n.equals("")) {
				JOptionPane.showMessageDialog(null, "주민번호를 입력해주세요.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
			}
		}

		if(obj == btnCancel) {
			dispose();
		}
	}



	private void Count() {
		String sql = "SELECT * FROM RENT ORDER BY RENT_NO";
		DB.executeQuery(sql);
		String c = "SELECT RENT_NO FROM(SELECT * FROM RENT ORDER BY ROWNUM DESC) WHERE ROWNUM = 1";
		ResultSet rs2 = DB.getResultSet(c);
		try {
			while(rs2.next()) {
				count = rs2.getInt("RENT_NO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





}
