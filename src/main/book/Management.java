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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Management extends JFrame implements ActionListener {
	private JPanel p1, p2;
	private JButton btnRefresh, btnRegistration, btnSearch, btnChange, btnDel, btnBack; // 새로고침, 등록, 조회, 수정, 삭제, 돌아가기 버튼
	private ResultSet rs;
	private String sql;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static String header[] = { "도서번호", "제목", "저자", "출판사", "가격", "대출여부" };

	public static DefaultTableModel getTableModel() {
		return tableModel;
	}

	public static String[] getHeader() {
		return header;
	}

	public static JTable getTable() {
		return table;
	}

	public Management() {
		setTitle("도서관리");
		setSize(500, 550);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 레이아웃
		setLayout(new BorderLayout());

		p1 = new JPanel();
		add(p1, BorderLayout.NORTH);

		btnRefresh = new JButton("새로고침");
		btnRefresh.addActionListener(this);

		btnRegistration = new JButton("등록");
		btnRegistration.addActionListener(this);

		btnSearch = new JButton("조회");
		btnSearch.addActionListener(this);

		btnChange = new JButton("수정");
		btnChange.addActionListener(this);

		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);

		btnBack = new JButton("돌아가기");
		btnBack.addActionListener(this);

		p1.add(btnRefresh);
		p1.add(btnRegistration);
		p1.add(btnSearch);
		p1.add(btnChange);
		p1.add(btnDel);
		p1.add(btnBack);

		p2 = new JPanel(new BorderLayout());
		p2.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "도서목록")); // p2에 테두리와 타이틀 생성
//		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		add(p2, BorderLayout.CENTER);
		tableModel = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) { // 테이블 수정 불가
				return false;
			}
		};

		sql = "SELECT*FROM LIB";
		rs = db.DB.getResultSet(sql);

		try { // DB 이용해 테이블 생성
			while (rs.next()) {
				String lib_code = rs.getString(1);
				String lib_name = rs.getString(2);
				String lib_author = rs.getString(3);
				String lib_publisher = rs.getString(4);
				int lib_price = rs.getInt(5);// 인덱스로 가져오고 싶으면 4 입력
				String lib_state = rs.getString(6);
				Object data[] = { lib_code, lib_name, lib_author, lib_publisher, lib_price, lib_state };
				tableModel.addRow(data);
//				System.out.println(lib_code + "\t" + lib_name + "\t" + lib_author + "\t" + lib_publisher + "\t"
//						+ lib_price + "\t" + lib_state);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 열의 이동 금지
		table.getTableHeader().setResizingAllowed(false); // 테이블 사이즈 고정
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 오직 한개의 row만 선택 가능
		table.setAutoCreateRowSorter(true);
		JScrollPane sc = new JScrollPane(table);
		p2.add(sc);

		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB.init();
		new Management();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnRefresh) { // 새로고침
			for (int i = 0; i < tableModel.getRowCount();) {
				tableModel.removeRow(i);
			}

			sql = "SELECT*FROM LIB";
			rs = db.DB.getResultSet(sql);
			try {
				while (rs.next()) {
					String lib_code = rs.getString(1);
					String lib_name = rs.getString(2);
					String lib_author = rs.getString(3);
					String lib_publisher = rs.getString(4);
					int lib_price = rs.getInt(5);
					String lib_state = rs.getString(6);
					Object data[] = { lib_code, lib_name, lib_author, lib_publisher, lib_price, lib_state };
					tableModel.addRow(data);

//				System.out.println(lib_code + "\t" + lib_name + "\t" + lib_author + "\t" + lib_publisher + "\t"
//						+ lib_price + "\t" + lib_state);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (obj == btnRegistration) {
//			dispose();
			new Registration();
		} else if (obj == btnSearch) {
			db.DB.init();
//			dispose();
			new Search();
		} else if (obj == btnChange) {
//			dispose();
			new Change();
		} else if (obj == btnDel) {
//			dispose();
			new Del();
		} else if (obj == btnBack) {
			dispose();
//			new Main("도서 관리 프로그램", 950, 700);
//			System.exit(0);
		}
	}

}
