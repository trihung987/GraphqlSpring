<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
body {
    background: rgb(99, 39, 120)
}

.form-control:focus {
    box-shadow: none;
    border-color: #BA68C8
}

.profile-button {
    background: rgb(99, 39, 120);
    box-shadow: none;
    border: none
}

.profile-button:hover {
    background: #682773
}

.profile-button:focus {
    background: #682773;
    box-shadow: none
}

.profile-button:active {
    background: #682773;
    box-shadow: none
}

.back:hover {
    color: #682773;
    cursor: pointer
}

.labels {
    font-size: 11px
}

.add-experience:hover {
    background: #BA68C8;
    color: #fff;
    cursor: pointer;
    border: solid 1px #BA68C8
}
</style>
<body>
	<form action="myaccount" method="post" enctype="multipart/form-data">
	<c:if test="${alert !=null}">
			<h1 class="alert alert danger">${alert}</h1>
		</c:if> 
	<!-- BEGIN CONTENT -->
          <div>
            <h1>My Account Page</h1>
            <div class="content-page">
              <h3>My Account</h3>
              <div class="container rounded bg-white mt-5 mb-5">
    <div class="row">
        <div class="col-md-3 border-right">
            <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150px" src="${pageContext.request.contextPath}/images/${sessionScope.account.images}">
            <input type="file" name="multiPartServlet"/>
            </div>
        </div>
        <div class="col-md-5 border-right">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Profile Settings</h4>
                </div>
                <div class="row mt-2">
                    <div class="col-md-6"><label class="labels">ID</label><input readonly type="text" name="id" class="form-control" value="${sessionScope.account.id}"></div>
                 </div>
                <div class="row mt-3">
                    <div class="col-md-12"><label class="labels">Full Name</label><input type="text" class="form-control" name="fullname"  value="${sessionScope.account.fullname}"></div>
                    <div class="col-md-12"><label class="labels">Username</label><input readonly type="text" class="form-control" name="username" value="${sessionScope.account.username}"></div>
                    <div class="col-md-12"><label class="labels">Email</label><input readonly type="text" class="form-control" name="email" value="${sessionScope.account.email}"></div>
                    <div class="col-md-12"><label class="labels">Phone Number</label><input type="text" class="form-control" name="phone" value="${sessionScope.account.phone}"></div>
                    <div class="col-md-12"><label class="labels">Create date</label><input readonly type="text" class="form-control" name="createDate" value="${sessionScope.account.createDate}"></div>
                    </div>
                </br>
                <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="submit">Save Profile</button></div>
            </div>
        </div>
    </div>
</div>
</form>
              <hr>

              <h3>My Orders</h3>
              <ul>
                <li><a href="#">View your order history</a></li>
                <li><a href="#">Downloads</a></li>
                <li><a href="#">Your Reward Points</a></li>
                <li><a href="#">View your return requests</a></li>
                <li><a href="#">Your Transactions</a></li>
              </ul>
            </div>
          </div>
          <!-- END CONTENT -->
</body>
</html>