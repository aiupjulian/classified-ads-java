<%@page language="java" isErrorPage="true" import="java.io.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="./WEB-INF/jsp/layout/header.jsp" />
<style><%@ include file="./css/error.css" %></style>
<div class="error-content">
  <h1>Hubo un error al procesar tu solicitud.</h1>
  <div><%=exception.getMessage()%></div>
</div>
<jsp:include page="./WEB-INF/jsp/layout/footer.jsp" />