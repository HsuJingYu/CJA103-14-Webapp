<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>

<%
  MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<title>會員資料 - listOneMember.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>會員資料 - listOneMember.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back_end/member/select_page.jsp"><img src="<%=request.getContextPath() %>/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>會員編號</th>
		<th>帳號</th>
		<th>密碼</th>
		<th>姓名</th>
		<th>地址</th>
		<th>電話</th>
		<th>代幣</th>
		<th>狀態</th>
		<th>創建時間</th>
	</tr>
	<tr>
		<td>${memberVO.memberId}</td>
		<td>${memberVO.account}</td>
		<td>${memberVO.password}</td>
		<td>${memberVO.name}</td>
		<td>${memberVO.address}</td>
		<td>${memberVO.phone}</td>
		<td>${memberVO.token}</td>
		<td>${(memberVO.status == 0) ? '正常' : '停權'}</td>
		<td>${memberVO.createdAt}</td>
		<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
	</tr>
</table>

</body>
</html>