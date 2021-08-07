package main.member;

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

public class MemberList extends JFrame implements ActionListener {
	private JPanel p1, p2;
	private JButton btnRefresh, btnRegistration, btnSearch, btnChange, btnDel, btnBack; // 새로고침, 등록, 조회, 수정, 삭제, 돌아가기 버튼
	private JLabel lbl;
	private ResultSet rs;
	private String sql;
	private Search mSeach;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static String header[] = { "이름", "주민번호", "전화번호", "주소" };

	public static DefaultTableModel getTableModel() {
		return tableModel;
	}

	public static String[] getHeader() {
		return header;
	}


	public static JTable getTable() {
		return table;
	}

	public MemberList() {
		setTitle("회원목록");
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
		p2.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "회원목록")); // p2에 테두리와 타이틀 생성
//		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		add(p2, BorderLayout.CENTER);
		tableModel = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) { // 테이블 수정 불가
				return false;
			}
		};

		sql = "SELECT*FROM MEMBER";
		rs = db.DB.getResultSet(sql);

		DrawTable(rs);

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
		new MemberList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnRefresh) { // 새로고침
			for (int i = 0; i < tableModel.getRowCount();) {
				tableModel.removeRow(i);
			}

			sql = "SELECT*FROM MEMBER";
			rs = db.DB.getResultSet(sql);
			DrawTable(rs);
		} else if (obj == btnRegistration) {
//			dispose();
			new Registration();
		} else if (obj == btnSearch) {
			db.DB.init();
//			dispose();
			new Search();
			
			//new Modify();
		} else if (obj == btnChange) {
//			dispose();
			new Modify();
		} else if (obj == btnDel) {
//			dispose();
			new Del();
		} else if (obj == btnBack) {
			dispose();
//			new Main("도서 관리 프로그램", 950, 700);
//			System.exit(0);
		}
	}

	private void DrawTable(ResultSet rs) { // 테이블 그리는 메소드
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

}
