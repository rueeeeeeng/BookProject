package main.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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

public class Registration extends JFrame implements ActionListener {
	private JPanel p1, p2, p3;
	private JLabel lbl1;
	private JButton btnOK, btnCancel;
	private JTextField jf1, jf2, jf3, jf4, jf5, jf6;
	private String UpdateSql;
	private boolean inputCheck;

	public Registration() {
		setTitle("도서등록");
		setSize(300, 250);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		add(p1, new BorderLayout().NORTH);

		lbl1 = new JLabel("도서등록");
		lbl1.setForeground(Color.WHITE);
		p1.add(lbl1);

		p2 = new JPanel(new GridLayout(6, 2));
		add(p2, BorderLayout.CENTER);

		JLabel lblNum = new JLabel("도서번호");
		p2.add(lblNum);

		jf1 = new JTextField();
		jf1.disable();
		SettingCode();

		JLabel lblTitle = new JLabel("제목");
		p2.add(lblTitle);

		jf2 = new JTextField();
		p2.add(jf2);

		JLabel lblWriter = new JLabel("저자");
		p2.add(lblWriter);

		jf3 = new JTextField();
		p2.add(jf3);

		JLabel lblCompany = new JLabel("출판사");
		p2.add(lblCompany);

		jf4 = new JTextField();
		p2.add(jf4);

		JLabel lblPrice = new JLabel("가격");
		p2.add(lblPrice);

		jf5 = new JTextField();
		p2.add(jf5);
		JLabel lblInfo = new JLabel("대여정보");
		p2.add(lblInfo);

		jf6 = new JTextField();
		p2.add(jf6);

		p3 = new JPanel();
		add(p3, BorderLayout.SOUTH);

		btnOK = new JButton("확인");
		btnOK.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		p3.add(btnOK);
		p3.add(btnCancel);
		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB.init();
		new Registration();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String code = "", name = "", author = "", publisher = "", state = "";
		int price = 0;
		if (obj == btnOK) {
			if (!(jf6.getText().equalsIgnoreCase("Y") || jf6.getText().equalsIgnoreCase("N"))) {
				JOptionPane.showMessageDialog(null, "다시 입력해주세요");
				jf6.setText("");
			} else {
				code = jf1.getText();
				name = jf2.getText();
				author = jf3.getText();
				publisher = jf4.getText();
				price = Integer.parseInt(jf5.getText()); // jf5 숫자만 입력하게 제한?
				state = jf6.getText().toUpperCase(); // 대문자 변환

				
				UpdateSql = "INSERT INTO F11.LIB VALUES" + "('" + code + "', '" + name + "', '" + author + "', '"
						+ publisher + "', " + price + ", '" + state + "')";
				db.DB.executeQuery(UpdateSql);

				JOptionPane.showMessageDialog(null, "입력이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				String sql = "SELECT*FROM LIB";
				ResultSet rs = db.DB.getResultSet(sql);
				
				Draw(rs);

				SettingCode(); // 도서번호 재입력
//					jf1.setText("");
				jf2.setText("");
				jf3.setText("");
				jf4.setText("");
				jf5.setText("");
				jf6.setText("");
			}
//			}

		} else if (obj == btnCancel) {
			dispose();
		}
	}

	private boolean Check(ResultSet rs) { // 중복 확인
		try {
			if (rs.next()) { // 입력된 것이 디비에 있는지 확인
				inputCheck = true;
			} else {
				inputCheck = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inputCheck;
	}

	private void Draw(ResultSet rs) {
		for (int i = 0; i < Management.getTableModel().getRowCount();) {
			Management.getTableModel().removeRow(i);
		}
		try {
			while (rs.next()) {
				String lib_code = rs.getString(1);
				String lib_name = rs.getString(2);
				String lib_author = rs.getString(3);
				String lib_publisher = rs.getString(4);
				int lib_price = rs.getInt(5);
				String lib_state = rs.getString(6);
				Object data[] = { lib_code, lib_name, lib_author, lib_publisher, lib_price, lib_state };
				Management.getTableModel().addRow(data);
//			System.out.println(lib_code + "\t" + lib_name + "\t" + lib_author + "\t" + lib_publisher + "\t"
//					+ lib_price + "\t" + lib_state);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void SettingCode() { // 도서번호 세팅 메서드
		String sql = "SELECT MAX(LIB_CODE) FROM LIB";
		ResultSet rs = db.DB.getResultSet(sql);
		String code = "";
		try {
			while (rs.next()) {
				code = rs.getString(1);
				System.out.println(code);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String c = code.substring(1, code.length());
		int num = Integer.parseInt(c) + 1;
		code = code.substring(0, 2) + num + "";
		jf1.setText(code);
		p2.add(jf1);
	}
}


