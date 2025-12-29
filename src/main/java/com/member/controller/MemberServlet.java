package com.member.controller;

import java.io.*;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.WebServlet;

import com.member.model.*;

//@WebServlet("/member/member.do")
public class MemberServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("memberId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入會員編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("會員編號格式不正確");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/***************************2.開始查詢資料*****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberId);
			if (memberVO == null) {
				errorMsgs.add("查無資料");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("memberVO", memberVO);
			String url = "/back_end/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/***************************1.接收請求參數****************************************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));

			/***************************2.開始查詢資料****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberId);

			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("memberVO", memberVO);
			String url = "/back_end/member/update_member_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_member_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId").trim());

			String account = req.getParameter("account");
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			}

			String password = req.getParameter("password");
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			}
			
			String name = req.getParameter("name");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("會員姓名: 請勿空白");
			} else if(!name.trim().matches(nameReg)) {
				errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String address = req.getParameter("address").trim();
			// address 可以為空，視業務邏輯而定

			String phone = req.getParameter("phone").trim();
			// phone 可以做正則驗證，這裡暫時略過

			Integer token = null;
			try {
				token = Integer.valueOf(req.getParameter("token").trim());
			} catch (NumberFormatException e) {
				token = 0;
				errorMsgs.add("代幣請填數字");
			}

			Integer status = null;
			try {
				status = Integer.valueOf(req.getParameter("status").trim());
			} catch (NumberFormatException e) {
				status = 0;
				errorMsgs.add("狀態請填數字");
			}

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(memberId);
			memberVO.setAccount(account);
			memberVO.setPassword(password);
			memberVO.setName(name);
			memberVO.setAddress(address);
			memberVO.setPhone(phone);
			memberVO.setToken(token);
			memberVO.setStatus(status);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/member/update_member_input.jsp");
				failureView.forward(req, res);
				return;
			}

			/***************************2.開始修改資料*****************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.updateMember(memberId, account, password, name, address, phone, token, status);

			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			req.setAttribute("memberVO", memberVO);
			String url = "/back_end/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addMember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			String account = req.getParameter("account");
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			}

			String password = req.getParameter("password");
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			}
			
			String name = req.getParameter("name");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("會員姓名: 請勿空白");
			} else if(!name.trim().matches(nameReg)) {
				errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String address = req.getParameter("address").trim();
			String phone = req.getParameter("phone").trim();

			Integer token = null;
			try {
				String tokenStr = req.getParameter("token");
				if(tokenStr == null || tokenStr.trim().isEmpty()) {
					token = 0; // default
				} else {
					token = Integer.valueOf(tokenStr.trim());
				}
			} catch (NumberFormatException e) {
				token = 0;
				errorMsgs.add("代幣請填數字");
			}

			Integer status = null;
			try {
				String statusStr = req.getParameter("status");
				if(statusStr == null || statusStr.trim().isEmpty()) {
					status = 0; // default
				} else {
					status = Integer.valueOf(statusStr.trim());
				}
			} catch (NumberFormatException e) {
				status = 0;
				errorMsgs.add("狀態請填數字");
			}

			MemberVO memberVO = new MemberVO();
			memberVO.setAccount(account);
			memberVO.setPassword(password);
			memberVO.setName(name);
			memberVO.setAddress(address);
			memberVO.setPhone(phone);
			memberVO.setToken(token);
			memberVO.setStatus(status);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/member/addMember.jsp");
				failureView.forward(req, res);
				return;
			}

			/***************************2.開始新增資料***************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.addMember(account, password, name, address, phone, token, status);

			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/back_end/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllMember.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/***************************1.接收請求參數***************************************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));

			/***************************2.開始刪除資料***************************************/
			MemberService memberSvc = new MemberService();
			memberSvc.deleteMember(memberId);

			/***************************3.刪除完成,準備轉交(Send the Success view)***********/
			String url = "/back_end/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
	}
}