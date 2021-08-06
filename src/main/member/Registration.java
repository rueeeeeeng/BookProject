package main.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Registration extends JFrame implements ActionListener, ItemListener {
	private JPanel p1, p2, p3, addrPanel;
	private JLabel lblTitle;
	private JButton btnOK, btnCancel;
	private JTextField jf1, jf2, jf3;
	private String UpdateSql;
	private boolean inputCheck;
	private DefaultTableModel tableModel;
	private JComboBox<String> city;
	private JComboBox<String> district;

	String[] citys = { "시/도 선택", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도",
			"충청북도", "충청남도", "전라북도", "전라남도", "경상북도	", "경상남도", "제주특별자치도" };
	String[] disGwangju = { "동구", "서구", "남구", "북구", "광산구" };
	Vector<String> disVec = new Vector<String>(1);

	public Registration() {
		setTitle("회원등록");
		setSize(450, 300);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		add(p1, new BorderLayout().NORTH);

		lblTitle = new JLabel("회원등록");
		lblTitle.setForeground(Color.WHITE);
		p1.add(lblTitle);

		p2 = new JPanel(new GridLayout(4, 2));
		add(p2, BorderLayout.CENTER);

		JLabel lblName = new JLabel("이름: ");
		p2.add(lblName);

		jf1 = new JTextField();
		p2.add(jf1);

		JLabel lblNum = new JLabel("주민번호: ");
		p2.add(lblNum);

		jf2 = new JTextField();
		p2.add(jf2);

		JLabel lblTel = new JLabel("연락처: ");
		p2.add(lblTel);

		jf3 = new JTextField();
		p2.add(jf3);

		JLabel lblAddr = new JLabel("주소: ");
		p2.add(lblAddr);

		addrPanel = new JPanel(new GridLayout(1, 2));

		city = new JComboBox<String>(citys);
		addrPanel.add(city);
		city.addActionListener(this);
		city.addItemListener(this);
		disVec.add("구 선택");
		district = new JComboBox<String>(disVec);
		district.addActionListener(this);
		addrPanel.add(district);

		p2.add(addrPanel);
//		jf4 = new JTextField();
//		p2.add(jf4);

		p3 = new JPanel();
		add(p3, BorderLayout.SOUTH);

		btnOK = new JButton("확인");
		btnOK.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		p3.add(btnOK);
		p3.add(btnCancel);

		tableModel = MemberList.getTableModel();
		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB.init();
		new Registration();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String name = "", num = "", phone = "", addr = "";
		if (obj == city) { // 첫 번째 콤보박스의 이벤트
			int selectedIndex = city.getSelectedIndex();
			String selectedCity = city.getSelectedItem().toString();
			String sql = "SELECT * FROM F11.CITYS";
			ResultSet rs = db.DB.getResultSet(sql);

			try {
				while (rs.next()) {
					int index = rs.findColumn(selectedCity);
					String city = rs.getString(index);
					if (selectedIndex == index) {
						if (city != null) {
							disVec.add(city);
						
						}
					}
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		} else if (obj == btnOK) {
			name = jf1.getText();
			num = jf2.getText();
			phone = jf3.getText();
			addr = city.getSelectedItem().toString()+" "+district.getSelectedItem().toString();
			

			String sql = "SELECT * FROM MEMBER WHERE MB_NUM ='" + num + "'";
			ResultSet rs = db.DB.getResultSet(sql);
			boolean check = Check(rs); // 중복 체크
			if (check) {
				JOptionPane.showMessageDialog(null, "이미 등록된 주민번호입니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				jf1.setText("");
			} else {
				// 등록
				UpdateSql = "INSERT INTO F11.MEMBER VALUES" + "('" + name + "', '" + num + "', '" + phone + "', '"
						+ addr + "')";
				db.DB.executeQuery(UpdateSql);

				JOptionPane.showMessageDialog(null, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);

				sql = "SELECT*FROM MEMBER";
				rs = db.DB.getResultSet(sql);
				Draw(rs);

				jf1.setText("");
				jf2.setText("");
				jf3.setText("");
			}
		} else if (obj == btnCancel) {
			dispose();
		}
	}

	private void Draw(ResultSet rs) { // 테이블 그리는 메소드
		for (int i = 0; i < MemberList.getTableModel().getRowCount();) {
			MemberList.getTableModel().removeRow(i);
		}
		try {
			while (rs.next()) { // DB이용해 테이블 생성
				String name = rs.getString(1);
				String num = rs.getString(2);
				String phoneNum = rs.getString(3);
				String address = rs.getString(4);

				Object data[] = { name, num, phoneNum, address };
				tableModel.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getItem();

		if (e.getStateChange() == ItemEvent.SELECTED) {
			String sql = "SELECT * FROM F11.CITYS";
			ResultSet rs = db.DB.getResultSet(sql);
			try {
				while (rs.next()) {
					if (disVec.size() > 1) {
						for (int i = 1; i < disVec.size(); i++) {
							disVec.remove(i);
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
