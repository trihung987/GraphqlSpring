<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<a href="/admin/categories/add">Add New Category</a>

<c:if test="${message!=null }">
	${message}
</c:if>

<table border="1" width="100%">
	<tr>
		<th>STT</th>
		<th>images</th>
		<th>category name</th>
		<th>Status</th>
		<th>Action</th>
	</tr>
	<c:forEach items="${list}" var="cate" varStatus="stt">
		<tr>
			<td>${stt.index+1}</td>
			<td>${cate.images}</td>
			<td>${cate.name}</td>
			<td>${cate.status}</td>
		</tr>
		<td>
			<a href="/admin/categories/edit/${cate.id}">edit </a>
			<a href="/admin/categories/delete/${cate.id}">delete</a>
		</td>
	</c:forEach>
</table>
