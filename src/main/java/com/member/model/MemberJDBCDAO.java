package com.member.model;

import java.util.*;
import java.sql.*;

public class MemberJDBCDAO implements MemberDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	// 假設你的資料庫名稱為 Momento，請根據實際情況修改
	String url = "jdbc:mysql://localhost:3306/Momento?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "cja10314"; // 請修改為你的密碼

	private static final String INSERT_STMT = 
		"INSERT INTO MEMBER (ACCOUNT, PASSWORD, NAME, ADDRESS, PHONE, TOKEN, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT MEMBER_ID, ACCOUNT, PASSWORD, NAME, ADDRESS, PHONE, TOKEN, STATUS, CREATED_AT FROM MEMBER ORDER BY MEMBER_ID";
	private static final String GET_ONE_STMT = 
		"SELECT MEMBER_ID, ACCOUNT, PASSWORD, NAME, ADDRESS, PHONE, TOKEN, STATUS, CREATED_AT FROM MEMBER WHERE MEMBER_ID = ?";
	private static final String DELETE = 
		"DELETE FROM MEMBER WHERE MEMBER_ID = ?";
	private static final String UPDATE = 
		"UPDATE MEMBER SET ACCOUNT=?, PASSWORD=?, NAME=?, ADDRESS=?, PHONE=?, TOKEN=?, STATUS=? WHERE MEMBER_ID = ?";

	@Override
	public void insert(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memberVO.getAccount());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getAddress());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setInt(6, memberVO.getToken());
			pstmt.setInt(7, memberVO.getStatus());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (con != null) {
				try { con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
	}

	@Override
	public void update(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memberVO.getAccount());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getAddress());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setInt(6, memberVO.getToken());
			pstmt.setInt(7, memberVO.getStatus());
			pstmt.setInt(8, memberVO.getMemberId());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (con != null) {
				try { con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
	}

	@Override
	public void delete(Integer memberId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memberId);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (con != null) {
				try { con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
	}

	@Override
	public MemberVO findByPrimaryKey(Integer memberId) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("MEMBER_ID"));
				memberVO.setAccount(rs.getString("ACCOUNT"));
				memberVO.setPassword(rs.getString("PASSWORD"));
				memberVO.setName(rs.getString("NAME"));
				memberVO.setAddress(rs.getString("ADDRESS"));
				memberVO.setPhone(rs.getString("PHONE"));
				memberVO.setToken(rs.getInt("TOKEN"));
				memberVO.setStatus(rs.getInt("STATUS"));
				memberVO.setCreatedAt(rs.getTimestamp("CREATED_AT"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (con != null) {
				try { con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("MEMBER_ID"));
				memberVO.setAccount(rs.getString("ACCOUNT"));
				memberVO.setPassword(rs.getString("PASSWORD"));
				memberVO.setName(rs.getString("NAME"));
				memberVO.setAddress(rs.getString("ADDRESS"));
				memberVO.setPhone(rs.getString("PHONE"));
				memberVO.setToken(rs.getInt("TOKEN"));
				memberVO.setStatus(rs.getInt("STATUS"));
				memberVO.setCreatedAt(rs.getTimestamp("CREATED_AT"));
				list.add(memberVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
			}
			if (con != null) {
				try { con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
		return list;
	}

	public static void main(String[] args) {

		MemberJDBCDAO dao = new MemberJDBCDAO();

		// 新增 (你可以取消註解來測試)
//		MemberVO memberVO1 = new MemberVO();
//		memberVO1.setAccount("testUser01");
//		memberVO1.setPassword("123456");
//		memberVO1.setName("測試人員1");
//		memberVO1.setAddress("台北市中山區");
//		memberVO1.setPhone("0912345678");
//		memberVO1.setToken(100);
//		memberVO1.setStatus(0);
//		dao.insert(memberVO1);

		// 修改 (你可以取消註解來測試)
//		MemberVO memberVO2 = new MemberVO();
//		memberVO2.setMemberId(1); // 請確認資料庫有這個ID
//		memberVO2.setAccount("updateUser01");
//		memberVO2.setPassword("654321");
//		memberVO2.setName("修改後人員");
//		memberVO2.setAddress("高雄市");
//		memberVO2.setPhone("0987654321");
//		memberVO2.setToken(200);
//		memberVO2.setStatus(1);
//		dao.update(memberVO2);

		// 刪除 (你可以取消註解來測試)
//		dao.delete(1); // 請確認資料庫有這個ID

		// 單筆查詢 (你可以取消註解來測試)
//		MemberVO memberVO3 = dao.findByPrimaryKey(1); // 請確認資料庫有這個ID
//		if (memberVO3 != null) {
//			System.out.print(memberVO3.getMemberId() + ",");
//			System.out.print(memberVO3.getAccount() + ",");
//			System.out.print(memberVO3.getPassword() + ",");
//			System.out.print(memberVO3.getName() + ",");
//			System.out.print(memberVO3.getAddress() + ",");
//			System.out.print(memberVO3.getPhone() + ",");
//			System.out.print(memberVO3.getToken() + ",");
//			System.out.print(memberVO3.getStatus() + ",");
//			System.out.println(memberVO3.getCreatedAt());
//			System.out.println("---------------------");
//		} else {
//			System.out.println("查無此會員");
//		}

		// 查詢全部 (這就是你要求的對應程式碼)
		List<MemberVO> list = dao.getAll();
		for (MemberVO aMember : list) {
			System.out.print(aMember.getMemberId() + ",");
			System.out.print(aMember.getAccount() + ",");
			System.out.print(aMember.getPassword() + ",");
			System.out.print(aMember.getName() + ",");
			System.out.print(aMember.getAddress() + ",");
			System.out.print(aMember.getPhone() + ",");
			System.out.print(aMember.getToken() + ",");
			System.out.print(aMember.getStatus() + ",");
			System.out.print(aMember.getCreatedAt());
			System.out.println();
		}
	}
}