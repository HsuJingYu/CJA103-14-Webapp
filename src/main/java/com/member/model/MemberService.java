package com.member.model;

import java.util.List;

public class MemberService {

	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberJDBCDAO();
	}

	public MemberVO addMember(String account, String password, String name, String address, String phone, Integer token, Integer status) {

		MemberVO memberVO = new MemberVO();

		memberVO.setAccount(account);
		memberVO.setPassword(password);
		memberVO.setName(name);
		memberVO.setAddress(address);
		memberVO.setPhone(phone);
		memberVO.setToken(token);
		memberVO.setStatus(status);
		dao.insert(memberVO);

		return memberVO;
	}

	public MemberVO updateMember(Integer memberId, String account, String password, String name, String address, String phone, Integer token, Integer status) {

		MemberVO memberVO = new MemberVO();

		memberVO.setMemberId(memberId);
		memberVO.setAccount(account);
		memberVO.setPassword(password);
		memberVO.setName(name);
		memberVO.setAddress(address);
		memberVO.setPhone(phone);
		memberVO.setToken(token);
		memberVO.setStatus(status);
		dao.update(memberVO);

		return memberVO;
	}

	public void deleteMember(Integer memberId) {
		dao.delete(memberId);
	}

	public MemberVO getOneMember(Integer memberId) {
		return dao.findByPrimaryKey(memberId);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}
}