package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import data.dto.NoticeDTO;
import data.dto.QuestionDTO;
import mysql.db.DBConnect;

public class QuestionDAO {
	
	DBConnect db = new DBConnect();
	
	//insert (������ �Է�)
	public void insertQuestion(QuestionDTO dto) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into question (subject, content, writer, pass, ref, write_day) values (?, ?, ?, ?, ?, now())";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter());
			pstmt.setString(4, dto.getPass());
			pstmt.setInt(5, dto.getRef());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//ref �ְ� ���
	public int getMaxRef() {
		QuestionDTO dto = new QuestionDTO();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select max(ref) from question";
		int ref=0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1);
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return ref;
	}
	
	public int updateParentInfo(String idx) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update question set ref=max(ref)+1";
		int num=0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		return num;
	}
	
	//��ü ������ ���� (����¡�� ���� ������ ������ �˾ƾ���)
	public int getTotalCount() {
		int n = 0;
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from question";

		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				n = rs.getInt(1);
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return n;
	}
	
	//QnA ������ ����Ʈ ��ȯ (QnA ��� ��¿�)
	public List<QuestionDTO> getList(int start, int perpage) {
		List<QuestionDTO> list = new Vector<QuestionDTO>();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from question order by ref desc, step asc, idx desc limit ?,?";

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, perpage);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QuestionDTO dto = new QuestionDTO();
				dto.setIdx(rs.getString("idx"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setRef(rs.getInt("ref"));
				dto.setStep(rs.getInt("step"));
				dto.setWriteDay(rs.getTimestamp("write_day"));
				//list�� �߰�
				list.add(dto);
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//idx�� �ش��ϴ� dto��ȯ (�Խ��� ����� Ŭ������ �� ��ȣ�� �ش��ϴ� ������ �����ֱ�����)
	public QuestionDTO getData(String idx) {
		QuestionDTO dto = new QuestionDTO();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from question where idx=?";

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, idx);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setIdx(rs.getString("idx"));
				dto.setWriter(rs.getString("writer"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setPass(rs.getString("pass"));
				dto.setWriteDay(rs.getTimestamp("write_day"));
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return dto;
	}
	
	//idx�� �ش��ϴ� ���� ��������(�θ���� ���� ��������)
	public QuestionDTO getSubPass(String idx) {
		QuestionDTO dto = new QuestionDTO();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select subject, pass, ref, step, reforder from question where idx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, idx);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setSubject(rs.getString("subject"));
				dto.setPass(rs.getString("pass"));
				dto.setRef(rs.getInt("ref"));
				dto.setStep(rs.getInt("step"));
				dto.setReforder(rs.getInt("reforder"));
			}
		} catch (Exception e) {
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return dto;
	}
	
	//�亯 insert
	public void insertAnswer(QuestionDTO dto) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into question (subject, content, writer, pass, ref, step, reforder, write_day) values (?, ?, ?, ?, ?, ?, ?, now())";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter());
			pstmt.setString(4, dto.getPass());
			pstmt.setInt(5, dto.getRef());
			pstmt.setInt(6, dto.getStep()+1);
			pstmt.setInt(7, dto.getReforder()+1);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//Q&A update(����)
	public void updateQuestion(QuestionDTO dto) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update question set subject=?, pass=?, content=? where idx=?";

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getIdx());

			pstmt.execute();
		} catch (Exception e) {
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	public void deleteQuestion(int ref) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from question where ref=?";

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,ref);
			
			pstmt.execute();
		} catch (Exception e) {
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	public void deleteAnswer(String idx) {
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from question where idx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,idx);
			
			pstmt.execute();
		} catch (Exception e) {
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	// idx�� �ۼ��� ���
	public String getNick(String idx) {
		String nick = "1";

		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select writer from question where idx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				nick = rs.getString("writer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return nick;
	}
}
