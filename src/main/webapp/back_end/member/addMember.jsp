<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
   MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料新增 - addMember.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>會員資料新增 - addMember.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="<%=request.getContextPath() %>/resources/images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/member/member.do" name="form1">
<table>
	<tr>
		<td>帳號:</td>
		<td><input type="TEXT" name="account" value="<%= (memberVO==null)? "testUser" : memberVO.getAccount()%>" size="45"/></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="TEXT" name="password" value="<%= (memberVO==null)? "123456" : memberVO.getPassword()%>" size="45"/></td>
	</tr>
	<tr>
		<td>會員姓名:</td>
		<td><input type="TEXT" name="name" value="<%= (memberVO==null)? "王小明" : memberVO.getName()%>" size="45"/></td>
	</tr>
	<tr>
		<td>地址:</td>
		<td><input type="TEXT" name="address" value="<%= (memberVO==null)? "台北市" : memberVO.getAddress()%>" size="45"/></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="phone" value="<%= (memberVO==null)? "0912345678" : memberVO.getPhone()%>" size="45"/></td>
	</tr>
	<tr>
		<td>代幣:</td>
		<td><input type="TEXT" name="token" value="<%= (memberVO==null)? "0" : memberVO.getToken()%>" size="45"/></td>
	</tr>
	<tr>
		<td>狀態:</td>
		<td>
			<select size="1" name="status">
				<option value="0" ${(memberVO.status==0)?'selected':'' }>正常</option>
				<option value="1" ${(memberVO.status==1)?'selected':'' }>停權</option>
			</select>
		</td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>

</body>
</html>