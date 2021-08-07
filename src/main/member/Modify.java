package main.member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DB;

public class Modify extends JFrame implements ActionListener{
	private JPanel p1, p2, p3, p4, p5, p6, p7;
	private JTextField tf1, tf2, tf3, tf4, tf5;
	private JButton b1, b2, b3;
	private String name, num, phone, add;
//	private JComboBox<String> cb1, cb2;
//	private String[] addr1;
//	private Vector<String> v;
	public Modify() {
		setTitle("회원정보 수정");
		setSize(380, 230);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);
		
		p1 = new JPanel();
		add(p1, BorderLayout.NORTH);
		
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);
		p2.setLayout(new GridLayout(4,1));
		
		p3 = new JPanel();
		add(p3, BorderLayout.SOUTH);
		
		p4 = new JPanel();
		p2.add(p4);
		
		p5 = new JPanel();
		p2.add(p5);
		
		p6 = new JPanel();
		p2.add(p6);
		
		p7 = new JPanel();
		p2.add(p7);
		
		JLabel lb1 = new JLabel("회원 주민번호 : ");
		tf1 = new JTextField(10);
		b1 = new JButton("검색");
		
		p1.add(lb1);
		p1.add(tf1);
		p1.add(b1);
		
		JLabel lb2 = new JLabel("이름 : ");
		tf2 = new JTextField(15);
		
		JLabel lb3 = new JLabel("주민번호 : ");
		tf3 = new JTextField(15);
		
		JLabel lb4 = new JLabel("연락처 : ");
		tf4 = new JTextField(15);
		
		JLabel lb5 = new JLabel("주소 : ");
		tf5 = new JTextField(15);
		
//		findCol();
//		
//		cb1 = new JComboBox<String>(addr1);
//		cb1.addItemListener(this);
//		cb1.setPreferredSize(new Dimension(115,20));
		
		
		p4.add(lb2);
		p4.add(tf2);
		p5.add(lb3);
		p5.add(tf3);
		p6.add(lb4);
		p6.add(tf4);
		p7.add(lb5);
		//p7.add(cb1);
		p7.add(tf5);
		
//		v = new Vector<String>();
//		//first();
//		cb2 = new JComboBox<String>(v);
//		cb2.setPreferredSize(new Dimension(115,20));
//		p7.add(cb2);
		
		
		b2 = new JButton("수정");
		b3 = new JButton("돌아가기");
		
		p3.add(b2);
		p3.add(b3);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		
		setVisible(true);
	}

//	private void first() {
//		v.clear();
//		String sql = "SELECT 서울특별시 FROM CITYS WHERE 서울특별시 IS NOT NULL";
//		ResultSet rs = DB.getResultSet(sql);
//		try {
//			while(rs.next()) {
//				//System.out.print(rs.getString("부산광역시") + ", ");
//				v.add(rs.getString("서울특별시"));
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//	}
//
//	private void findCol() {
//		String sql = "SELECT * FROM CITYS";
//		ResultSet rs = DB.getResultSet(sql);
//		
//		try {
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int col = rsmd.getColumnCount();
//			addr1 = new String[col];
//			if(rs.next()) {
//				for(int i=1; i<=col; i++) {
//					addr1[i-1] = rsmd.getColumnName(i);
//				}
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}

	public static void main(String[] args) {
		DB.init();
		new Modify();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String snum = tf1.getText();
		//int index = cb1.getSelectedIndex();
		if(obj == b1) {
			boolean check = checkNum(snum);
			if(check == true) {
				String sql = "SELECT MB_NAME, MB_NUM, MB_PHONE, MB_ADDR FROM MEMBER WHERE MB_NUM = '" + snum + "'";
				ResultSet rs = DB.getResultSet(sql);
				try {
					while(rs.next()) {
						String name = rs.getString("MB_NAME");
						String num = rs.getString("MB_NUM");
						String phone = rs.getString("MB_PHONE");
						String add = rs.getString("MB_ADDR");
						tf2.setText(name);
						tf3.setText(num);
						tf4.setText(phone);
						tf5.setText(add);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				 JOptionPane.showMessageDialog(null, "해당하는 회원의 정보가 없습니다!");
			}

		} else if(obj == b2) {
			String reName = tf2.getText();
			String reNum = tf3.getText();
			String rePhone = tf4.getText();
			String reAddr = tf5.getText();
			snum = tf1.getText();
			
			String sql = "UPDATE MEMBER SET MB_NAME = '" + reName + "', MB_NUM = '" + reNum + "', MB_PHONE ='" 
			+ rePhone  + "', MB_ADDR = '" + reAddr + "' WHERE MB_NUM ='" + snum + "'";
			DB.executeQuery(sql);
			
			JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			tf5.setText("");
		} else if(obj == b3) {
			dispose();
		}
		

	}

	private boolean checkNum(String snum) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE MB_NUM = '" + snum + "'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
				check = true;
			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}



//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		if(cb1.getSelectedIndex() == 0) {
//			System.out.println(cb1.getSelectedIndex());
//	 		v.clear();
//				String sql = "SELECT 서울특별시 FROM CITYS WHERE 서울특별시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("서울특별시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 1) {
// 		v.clear();
//			String sql = "SELECT 부산광역시 FROM CITYS WHERE 부산광역시 IS NOT NULL";
//			ResultSet rs = DB.getResultSet(sql);
//			try {
//				while(rs.next()) {
//					v.add(rs.getString("부산광역시"));
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//		else if(cb1.getSelectedIndex() == 2) {
//	 		v.clear();
//				String sql = "SELECT 대구광역시 FROM CITYS WHERE 대구광역시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("대구광역시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 3) {
//	 		v.clear();
//				String sql = "SELECT 인천광역시 FROM CITYS WHERE 인천광역시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("인천광역시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 4) {
//	 		v.clear();
//				String sql = "SELECT 광주광역시 FROM CITYS WHERE 광주광역시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("광주광역시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 5) {
//	 		v.clear();
//				String sql = "SELECT 대전광역시 FROM CITYS WHERE 대전광역시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("대전광역시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 6) {
//	 		v.clear();
//				String sql = "SELECT 울산광역시 FROM CITYS WHERE 울산광역시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("울산광역시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 7) {
//	 		v.clear();
//				String sql = "SELECT 세종특별자치시 FROM CITYS WHERE 세종특별자치시 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("세종특별자치시"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 8) {
//	 		v.clear();
//				String sql = "SELECT 경기도 FROM CITYS WHERE 경기도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("경기도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 9) {
//	 		v.clear();
//				String sql = "SELECT 강원도 FROM CITYS WHERE 강원도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("강원도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 10) {
//	 		v.clear();
//				String sql = "SELECT 충청북도 FROM CITYS WHERE 충청북도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("충청북도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 11) {
//	 		v.clear();
//				String sql = "SELECT 충청남도 FROM CITYS WHERE 충청남도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("충청남도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 12) {
//	 		v.clear();
//				String sql = "SELECT 전라북도 FROM CITYS WHERE 전라북도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("전라북도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 13) {
//	 		v.clear();
//				String sql = "SELECT 전라남도 FROM CITYS WHERE 전라남도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("전라남도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 14) {
//	 		v.clear();
//				String sql = "SELECT 경상북도 FROM CITYS WHERE 경상북도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("경상북도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 15) {
//	 		v.clear();
//				String sql = "SELECT 경상남도 FROM CITYS WHERE 경상남도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("경상남도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		else if(cb1.getSelectedIndex() == 16) {
//	 		v.clear();
//				String sql = "SELECT 제주특별자치도 FROM CITYS WHERE 제주특별자치도 IS NOT NULL";
//				ResultSet rs = DB.getResultSet(sql);
//				try {
//					while(rs.next()) {
//						v.add(rs.getString("제주특별자치도"));
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//	}

}
