package main.book;

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

public class Del extends JFrame implements ActionListener {
	private JButton btnDel;
	private JButton btnCancel;
	private JTextField tfNum;
	private boolean Check;

	public Del() {
		setTitle("도서삭제");
		setSize(350, 250);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 레이아웃
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		JLabel lblDel = new JLabel("도서삭제");
		lblDel.setForeground(Color.WHITE);
		p1.add(lblDel);

		JPanel p2 = new JPanel();
		JLabel lblNum = new JLabel("도서번호");
		p2.add(lblNum, BorderLayout.NORTH);

		tfNum = new JTextField(12);
		p2.add(tfNum, BorderLayout.NORTH);

		JPanel p3 = new JPanel();
		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		p3.add(btnDel);
		p3.add(btnCancel);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Del();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnDel) {
			if (tfNum.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "도서번호를 입력하세요");
			} else {
				String code = tfNum.getText();
				String sql = "DELETE FROM F11.LIB WHERE LIB_CODE='" + code + "'";
				ResultSet rs = db.DB.getResultSet(sql);
				boolean check = Check(rs);
				if (check) {
					db.DB.executeQuery(sql);
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "검색하신 도서가 없습니다.");

			}

		} else if (obj == btnCancel) {
			dispose();
		}
	}

	private boolean Check(ResultSet rs) { // 중복 확인
		try {
			if (rs.next()) { // 입력된 것이 디비에 있는지 확인
				Check = true;
			} else {
				Check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Check;
	}

}

//도서번호 소문자로 입력 시 대문자로 변환
//String alpha = code.substring(0,1).toUpperCase();
//System.out.println(alpha);
//System.out.println(code.substring(1,code.length()));
//code=alpha.concat(code.substring(1,code.length())); 
//System.out.println(code);

//String sql1 = "SELECT * FROM LIB WHERE LIB_CODE ='" + code + "'";
//ResultSet rs1 = db.DB.getResultSet(sql1);
//boolean check = Check(rs1); // 중복 체크
//if (check) {
//	JOptionPane.showMessageDialog(null, "중복된 도서번호가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
//	jf1.setText("");
//} else {
// 등록
