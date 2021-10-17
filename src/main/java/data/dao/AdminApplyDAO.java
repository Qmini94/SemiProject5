package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import data.dto.AdminApplyDTO;
import mysql.db.DBConnect;

public class AdminApplyDAO {
	DBConnect db = new DBConnect();

	// ��ü ȸ�� ���
	public ArrayList<AdminApplyDTO> getAllMembers() {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from member order by idx desc";

		ArrayList<AdminApplyDTO> list = new ArrayList<AdminApplyDTO>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AdminApplyDTO dto = new AdminApplyDTO();

				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setNick(rs.getString("nick"));
				dto.setHp(rs.getString("hp"));
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setAuth1(rs.getString("auth1"));
				dto.setAuth2(rs.getString("auth2"));

				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}

		return list;
	}
	
	
	
	
	// ��ȸ�� ȸ�� ���
		public ArrayList<AdminApplyDTO> getSelectMembers(String tname) {
			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String sql = "select * from apply where tname=? order by idx desc";

			ArrayList<AdminApplyDTO> list = new ArrayList<AdminApplyDTO>();
			
			System.out.println(tname);

			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, tname);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					AdminApplyDTO dto = new AdminApplyDTO();

					dto.setAidx(rs.getString("idx"));
					dto.setAmarathon(rs.getString("marathon"));
					dto.setAid(rs.getString("id"));
					dto.setAname(rs.getString("name"));
					dto.setAaddr(rs.getString("addr"));
					dto.setAhp(rs.getString("hp"));
					dto.setAcourse(rs.getString("course"));
					dto.setAtime(rs.getString("time"));
					dto.setAperson(rs.getString("person"));

					list.add(dto);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db.dbClose(rs, pstmt, conn);
			}

			return list;
		}
	
	
	
	
	
	
	
	// ��ü ��ȸ ��ư ���
		public ArrayList<AdminApplyDTO> getContestName() {
			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String sql = "select * from contest order by contest_start";

			ArrayList<AdminApplyDTO> list = new ArrayList<AdminApplyDTO>();

			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					AdminApplyDTO dto = new AdminApplyDTO();

					dto.setContestName(rs.getString("name"));
					dto.setContestStart(rs.getString("contest_start"));
					dto.setContestEnd(rs.getString("contest_end"));
					dto.setApplyStart(rs.getString("apply_start"));
					dto.setApplyEnd(rs.getString("apply_end"));
					dto.setMoney(rs.getString("money"));

					list.add(dto);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db.dbClose(rs, pstmt, conn);
			}

			return list;
		}
	
		
		
		
		
		
		
		
		
		
	


	// �ش� ȸ�� id�� ���� ������ȸ��� ��ȯ
	// ������ ��ü�� �޾ƿ��� ���� �ƴ϶� marathon ������ �������� ���̱� ������
	// List ������ <String> ���� �������ش�.
	// ������ ��ü�� ���������� DTO�� ����
	public List<String> getMarathon(String id) {
		String marathon = "";
		List<String> list = new ArrayList<String>();
		// ���̵� ���� �Է¹޾� �ش� ���̵� ���� ������ list�� ��� ���� �������ش�.
		
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select marathon from participation where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// �ش� ȸ���� ���� ������ȸ��� ��ȯ
			while (rs.next()) {
				marathon = rs.getString("marathon");
				list.add(marathon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}

		return list;
	}

	
	
	
	
	
	// ȸ���� ���� ��� insert
	public void insertRecord(AdminApplyDTO dto) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;

		String sql = "insert into participation (name,marathon,course,contest_date,record) values (?,?,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getMarathon());
			pstmt.setString(3, dto.getCourse());
			pstmt.setTimestamp(4, dto.getContestDate());
			pstmt.setString(5, dto.getRecord());

			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);

		}
	}
	
	
	
	// ���������亯 üũ
		public boolean isPassEqual(String id, String pass) {
			boolean b = false;
			
			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "select * from member where id=? and pass=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					b = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db.dbClose(rs, pstmt, conn);
			}
			
			return b;
		}

	
	// ����
	public void deleteMember(String id) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;

		String sql = "delete from member where id=?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);

			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	
	
	// �˻�
	
	

	/*
	 * // ���� public void updateMember(MemberDto dto) { Connection conn =
	 * db.getConnection(); PreparedStatement pstmt = null;
	 * 
	 * String sql = "update member set name=?,hp=?,addr=?,email=? where num=?";
	 * 
	 * try { pstmt = conn.prepareStatement(sql);
	 * 
	 * pstmt.setString(1, dto.getName()); pstmt.setString(2, dto.getHp());
	 * pstmt.setString(3, dto.getAddr()); pstmt.setString(4, dto.getEmail());
	 * pstmt.setString(5, dto.getNum());
	 * 
	 * pstmt.execute(); } catch (SQLException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } finally { db.dbClose(pstmt, conn);
	 * 
	 * } }
	 */
}
