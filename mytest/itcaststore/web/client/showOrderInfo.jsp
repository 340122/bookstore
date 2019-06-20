<%--
  Created by IntelliJ IDEA.
  User: pc-student
  Date: 2019/4/8
  Time: 19:00
  To change this template use File | Settings | File Templates.
  订单信息页面（）
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="http://www.itcast.cn/tag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>电子书城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/client/css/main.css" type="text/css" />
    <script type="text/javascript" src="js/my.js">
    </script>
</head>
<body class="main">
<p:user/>
<jsp:include page="head.jsp" />
<jsp:include page="menu_search.jsp" />
<%
    request.setCharacterEncoding("utf-8");
    String receiverAddress = request.getParameter("receiverAddress");
    String receiverName = request.getParameter("receiverName");
    String receiverPhone = request.getParameter("receiverPhone");
    session.getAttribute("order");
   %>

<%--<script type="text/javascript">

alert(receiverAddress);
</script>--%>

<div id="divpagecontent">
    <table width="100%" border="0" cellspacing="0">
        <tr>
            <td>
                <div style="text-align:right; margin:5px 10px 5px 0px">
                    <a href="${pageContext.request.contextPath }/index.jsp">首页</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath }/client/myAccount.jsp">我的账户</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;订单详细信息
                </div>
                <table cellspacing="0" class="infocontent">
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <p>订单编号:${order.id}</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table cellspacing="1" class="carttable">
                                            <tr>
                                                <td width="10%">序号</td>
                                                <td width="40%">商品名称</td>
                                                <td width="10%">价格</td>
                                                <td width="10%">数量</td>
                                                <td width="10%">小计</td>
                                            </tr>
                                        </table>
                                        <c:forEach items="${order.orderItems}" var="item" varStatus="vs">
                                            <table width="100%" border="0" cellspacing="0">
                                                <tr>
                                                    <td width="10%">${vs.count }</td>
                                                    <td width="40%">${item.p.name}</td>
                                                    <td width="10%">${item.p.price }</td>
                                                    <td width="10%">${item.buynum }</td>
                                                    <td width="10%">${item.buynum*item.p.price }</td>
                                                </tr>
                                            </table>
                                        </c:forEach>
                                        <table cellspacing="1" class="carttable">
                                            <tr>
                                                <td style="text-align:right; padding-right:40px;"><font
                                                        style="color:#FF0000">合计：&nbsp;&nbsp;${order.money}</font>
                                                </td>
                                            </tr>
                                        </table>
                                        <p>
                                            收货地址：<%=receiverAddress%>&nbsp;&nbsp;&nbsp;&nbsp;<br />
                                            收货人：&nbsp;&nbsp;&nbsp;&nbsp;<%=receiverName%>&nbsp;&nbsp;&nbsp;&nbsp;<br />
                                            联系方式：<%=receiverPhone%>&nbsp;&nbsp;&nbsp;&nbsp;
                                        </p>
                                        <hr>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <font style="font-weight:bold; color:#FF0000">亲，订单生成成功，请到“我的账户”-“订单查询”中支付！</font><br /> <br />
    <a href="${pageContext.request.contextPath }/index.jsp">
    <span id="second">5</span>秒后自动为您转跳回首页</a>
</div>
<jsp:include page="foot.jsp" />
</body>
</html>

