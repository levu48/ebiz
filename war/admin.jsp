<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.kicon.ebiz.model.Shop" %>
<%@ page import="com.kicon.ebiz.model.Category" %>
<%@ page import="com.kicon.ebiz.model.CategoryElement" %>
<%@ page import="com.kicon.ebiz.model.Account" %>
<%@ page import="com.kicon.ebiz.model.User" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kicon.ebiz.server.RpcServiceImpl" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Java Persistence Test</title>
</head>
<body>
<h2>PT-POS Admin</h2>
<%

		//if (!"levu48@gmail.com".equals(PosAppServiceImpl.getLoginEmail())) {
		//	out.print("Not allowed.");
		//	return;
		//}


		String pId = request.getParameter("id");
		String pCmd = request.getParameter("cmd");
		String pEmail = request.getParameter("email");
		String pName = request.getParameter("name");
		String pDisplayName = request.getParameter("displayName");
		
		String PERSISTENCE_UNIT_NAME = "ebiz";
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		
		// PROCESSING
		em.getTransaction().begin();
		
		if (pCmd != null && pId != null) {
			if (pCmd.equals("approveAccount")) {
				Account account = em.find(Account.class, pId);
				if (account != null) {
					account.setLocked(false);
				}
				
			} else if (pCmd.equals("unapproveAccount")) {
				Account account = em.find(Account.class, pId);
				if (account != null) {
					account.setLocked(true);
				}
				
			} else if (pCmd.equals("deleteAccount")) {
				Account account = em.find(Account.class, pId);
				if (account != null) {
					em.remove(account);
				}
					
			} else if (pCmd.equals("deleteUser")) {
				User user = em.find(User.class, pId);
				if (user != null) {
					em.remove(user);
				}
				
			} else if (pCmd.equals("deleteShop")) {
				Shop shop = em.find(Shop.class, pId);
				if (shop != null) {
					em.remove(shop);
				}
			}
		}
		
		if (pCmd != null && pEmail != null) {
			if (pCmd.equals("register")) {
				Account account = new Account();
				account.setEmail(pEmail);
				account.setName(pName);
				account.setDisplayName(pDisplayName);
				account.setLocked(true);
				em.persist(account);
			}
		}
		
		em.getTransaction().commit();
		
		
		// DISPLAY
		
		Query query = em.createQuery("SELECT o FROM Account o order by o.locked");
		List<Account> accounts = query.getResultList();
		
		Query query2 = em.createQuery("SELECT o FROM User o order by o.email");
		List<User> users = query2.getResultList();
		
		Query query3 = em.createQuery("SELECT o FROM Shop o order by o.createdBy");
		List<Shop> shops = query3.getResultList();
		
		//Query query4 = em.createQuery("SELECT e FROM Category o, CategoryElement e where e.category = o");
		Query query4 = em.createQuery("SELECT e FROM CategoryElement e");
		List<CategoryElement> elements = query4.getResultList();
			
		em.close();
		
%>
<a href="/admin.jsp">reload</a><br>
<p>
ACCOUNTS<br>
<table style="border: 1px solid black">
<tbody>
<tr>
<th style="background-color: #CCFFCC; margin: 5px">id</th>
<th style="background-color: #CCFFCC; margin: 5px">email</th>
<th style="background-color: #CCFFCC; margin: 5px">name</th>
<th style="background-color: #CCFFCC; margin: 5px">displayName</th>
<th style="background-color: #CCFFCC; margin: 5px">locked</th>
<th style="background-color: #CCFFCC; margin: 5px">actions</th>

</tr> 
<%
	for(Account account:accounts) { %>
		<tr>
		<td><%=account.getId()%></td>
		<td><%=account.getEmail()%></td>
		<td><%=account.getName()%></td>
		<td><%=account.getDisplayName()%></td>
		<td><%=account.isLocked()%></td>
		<td><a href="/admin.jsp?cmd=approveAccount&id=<%=account.getId()%>">approve</a>,
		    <a href="/admin.jsp?cmd=unapproveAccount&id=<%=account.getId()%>">unapprove</a>,
		    <a href="/admin.jsp?cmd=deleteAccount&id=<%=account.getId()%>">delete</a>
		</td>
		</tr>

	<% }
%>
<form name="input" action="admin.jsp" method="get">
<input type="hidden" name="cmd" id="cmd" value="register"></th>
<tr>
<th></th>
<th><input type="text" name="email"></th>
<th><input type="text" name="name"></th>
<th><input type="text" name="displayName"></th>
<th></th>
<th><input type="submit" value="register"></th>
</tr>
</form>
</form>
</table>
<p>
USERS<br>
<table style="border: 1px solid black">
<tbody>
<tr>
<th style="background-color: #CCFFCC; margin: 5px">id</th>
<th style="background-color: #CCFFCC; margin: 5px">email</th>
<th style="background-color: #CCFFCC; margin: 5px">displayName</th>
<th style="background-color: #CCFFCC; margin: 5px">role</th>
<th style="background-color: #CCFFCC; margin: 5px">isLocked</th>
<th style="background-color: #CCFFCC; margin: 5px">shopId</th>
<th style="background-color: #CCFFCC; margin: 5px">actions</th>

</tr> 
<%
	for(User user:users) {
		String shopId = null;
		if (user.getShop() != null) {
			shopId = user.getShop().getId();
		}
		%>
		<tr>
		<td><%=user.getId()%></td>
		<td><%=user.getEmail()%></td>
		<td><%=user.getDisplayName()%></td>
		<td><%=user.getRole()%></td>
		<td><%=user.getIsLocked()%></td>
		<td><%=shopId%></td>
		<td><a href="/admin.jsp?cmd=deleteUser&id=<%=user.getId()%>">delete</a></td>
		</tr>

	<% }
%>
</table>

<p>
SHOPS<br>
<table style="border: 1px solid black">
<tbody>
<tr>
<th style="background-color: #CCFFCC; margin: 5px">id</th>
<th style="background-color: #CCFFCC; margin: 5px">name</th>
<th style="background-color: #CCFFCC; margin: 5px">createdBy</th>
<th style="background-color: #CCFFCC; margin: 5px">street</th>
<th style="background-color: #CCFFCC; margin: 5px">city</th>
<th style="background-color: #CCFFCC; margin: 5px">actions</th>

</tr> 
<%

	for (Shop shop:shops) {
		%>
		<tr>
		<td><%=shop.getId()%></td>
		<td><%=shop.getName()%></td>
		<td><%=shop.getCreatedBy()%></td>
		<td><%=shop.getRoad()%></td>
		<td><%=shop.getCity()%></td>
		
		<td><a href="/admin.jsp?cmd=deleteShop&id=<%=shop.getId()%>">delete</a>,
			<a href="/clearDB.jsp?id=<%=shop.getId()%>">clear DB</a>, 
			<a href="/data.jsp?id=<%=shop.getId()%>">add data set #1</a>,
			<a href="/data2.jsp?id=<%=shop.getId()%>">add data set #2</a>
		</td>
		</tr>
		<% 
	}
%>


</table>


<p>
CATEGORY ELEMENTS<br>
<table style="border: 1px solid black">
<tbody>
<tr>
<th style="background-color: #CCFFCC; margin: 5px">id</th>
<th style="background-color: #CCFFCC; margin: 5px">name</th>
<th style="background-color: #CCFFCC; margin: 5px">category</th>
<th style="background-color: #CCFFCC; margin: 5px">shop</th>
<th style="background-color: #CCFFCC; margin: 5px">actions</th>

</tr> 
<%

	for (CategoryElement el:elements) {
		%>
		<tr>
		<td><%=el.getId()%></td>
		<td><%=el.getName()%></td>
		<td><%=el.getCategory().getName()%></td>
		<td><%=el.getCategory().getShop().getName()%></td>
				
		<td><a href="/admin.jsp?cmd=deleteCategoryElement&id=<%=el.getId()%>">delete</a>,
		</td>
		</tr>
		<% 
	}
%>
</table>

<br />
<a href="/admin.jsp">reload</a><br>
Done executing ...
</body>
</html>