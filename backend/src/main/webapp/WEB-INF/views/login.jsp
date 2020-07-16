<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h4>로그인</h4>
	<hr>
	<form action="login" method="POST">
		<table style="border: 1px; margin: auto; text-align: center;">
			<tbody>
				<tr><td>ID</td><td><input type="text" name="uid"></td></tr>
				<tr><td>Password</td><td><input type="password" name="upw"></td></tr>
				<tr><td colspan="2"><button type="submit">로그인</button></td></tr>
			</tbody>
		</table>
	</form>
	<h4>비밀번호변경하러가기</h4>
	<hr>
	현재 비밀번호 : <input type="password" name="upw"> 여기도 프론트에서 해결하면 될듯
	
	<h4>비밀번호변경</h4>
	<hr>
	<form action="pwcheck" method="POST">
	
		변경 비밀번호 입력 : <input type="password" name="newupw1"></br> 
		변경 비밀번호 확인 : <input type="password" name="newupw2"></br>
		<button type="submit">변경</button> 
				
	</form>

</body>
</html>
