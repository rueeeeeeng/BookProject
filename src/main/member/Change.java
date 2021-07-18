package main.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Change extends JFrame implements ActionListener {
	private JTextField tfCode;
	private JButton btnSearch;
	private JTextField tfSearch;
	private JTextField tfTitle;
	private JTextField tfWriter;
	private JTextField tfPublisher;
	private JTextField tfPrice;
	private JTextField tfRent;
	private JButton btnChange;
	private JButton btnBack;
	private DefaultTableModel tableModel;

	public Change() {
		setTitle("도서정보 수정");
		setSize(320, 250);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 레이아웃
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		JLabel lblNum = new JLabel("도서번호: ");
		p1.add(lblNum);

		tfSearch = new JTextField(13);
		p1.add(tfSearch);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		p1.add(btnSearch);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(6, 2));
//		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		JLabel lblBookNum = new JLabel("도서번호");
		p2.add(lblBookNum);

		tfCode = new JTextField(15);
		p2.add(tfCode);

		JLabel lblTitle = new JLabel("제목");
		p2.add(lblTitle);

		tfTitle = new JTextField(15);
		p2.add(tfTitle);

		JLabel lblWriter = new JLabel("저자");
		p2.add(lblWriter);
		tfWriter = new JTextField();
		p2.add(tfWriter);

		JLabel lblPublisher = new JLabel("출판사");
		p2.add(lblPublisher);
		tfPublisher = new JTextField(15);
		p2.add(tfPublisher);

		JLabel lblPrice = new JLabel("가격");
		p2.add(lblPrice);
		tfPrice = new JTextField(15);
		p2.add(tfPrice);

		JLabel lblRent = new JLabel("대여여부");
		p2.add(lblRent);
		tfRent = new JTextField(15);
		p2.add(tfRent);

		JPanel p3 = new JPanel();

		btnChange = new JButton("수정");
		btnChange.addActionListener(this);
		p3.add(btnChange);

		btnBack = new JButton("돌아가기");
		btnBack.addActionListener(this);
		p3.add(btnBack);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);

	}

	public static void main(String[] args) {
		new Change();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnSearch) {
			search();
		} else if (obj == btnChange) {
			String code = tfCode.getText();
			String title = tfTitle.getText();
			String writer = tfWriter.getText();
			String publisher = tfPublisher.getText();
			int price = Integer.parseInt(tfPrice.getText());
			String rent = tfRent.getText();

			String sql = "UPDATE F11.LIB SET LIB_NAME='" + title + "', LIB_AUTHOR='" + writer + "', LIB_PUBLISHER='"
					+ publisher + "'," + " LIB_PRICE=" + price + ", LIB_STATE='" + rent + "' WHERE LIB_CODE='" + code
					+ "'";
			db.DB.executeQuery(sql);
			JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);

		} else if (obj == btnBack) {
			dispose();
		}
	}

	public void search() {
		if (tfSearch.getText().equals("")) { // 검색어를 입력하지 않았을 때
			JOptionPane.showMessageDialog(null, "도서번호를 입력해주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
		} else { // 검색어를 입력했을때
			String lib_searchCode = tfSearch.getText();
			String sql = "SELECT * FROM LIB WHERE LIB_CODE LIKE'%" + lib_searchCode + "%'";
			ResultSet rs = db.DB.getResultSet(sql);
			try {
				while (rs.next()) {
					String lib_code = rs.getString(1);
					String lib_name = rs.getString(2);
					String lib_author = rs.getString(3);
					String lib_publisher = rs.getString(4);
					String lib_price = rs.getString(5);
					String lib_state = rs.getString(6);

					tfCode.setText(lib_code);
					tfTitle.setText(lib_name);
					tfWriter.setText(lib_author);
					tfPublisher.setText(lib_publisher);
					tfPrice.setText(lib_price);
					tfRent.setText(lib_state);

//					Object data[] = { lib_code, lib_name, lib_author, lib_publisher, lib_price, lib_state };
//					tableModel.addRow(data);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
