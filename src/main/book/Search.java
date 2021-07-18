package main.book;

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

public class Search extends JFrame implements ActionListener {
	private JButton btnSearch;
	private JButton btnCancel;
	private DefaultTableModel tableModel;
	private JTextField tfTitle;

	public Search() {
		setTitle("도서검색");
		setSize(350, 250);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 레이아웃
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		JLabel lblSearch = new JLabel("도서검색");
		lblSearch.setForeground(Color.WHITE);
		p1.add(lblSearch);

		JPanel p2 = new JPanel();
		JLabel lblTitle = new JLabel("도서제목 : ");
		p2.add(lblTitle, BorderLayout.NORTH);

		tfTitle = new JTextField(12);
		p2.add(tfTitle, BorderLayout.NORTH);

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
		if (tfTitle.getText().equals("")) { // 검색어를 입력하지 않았을 때
			JOptionPane.showMessageDialog(null, "검색어를 입력해주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
		} else { // 검색어를 입력했을때
			String lib_title = tfTitle.getText();
			String sql = "SELECT * FROM LIB WHERE LIB_NAME LIKE'%" + lib_title + "%'";
			ResultSet rs = db.DB.getResultSet(sql);

			for (int i = 0; i < tableModel.getRowCount();) {
				tableModel.removeRow(i);
			}
			try {
				while (rs.next()) {
					String lib_code = rs.getString(1);
					String lib_name = rs.getString(2);
					String lib_author = rs.getString(3);
					String lib_publisher = rs.getString(4);
					String lib_price = rs.getString(5);
					String lib_state = rs.getString(6);
					Object data[] = { lib_code, lib_name, lib_author, lib_publisher, lib_price, lib_state };
					tableModel.addRow(data);
				}
				JOptionPane.showMessageDialog(null, "검색이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE); // 버튼 확인으로
																												// 바꾸기
			} catch (SQLException e) {
				e.printStackTrace();
			}

			tfTitle.setText("");

		}

	}
}
