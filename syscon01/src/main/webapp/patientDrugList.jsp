<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>処方薬一覧</title>
    <style>
      table { border-collapse: collapse; width: 100%; }
      th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
      .error { color: red; }
    </style>
  </head>
  <body>
    <h2>処方薬一覧</h2>
    <p>患者ID: ${patid}</p>
    <p>患者名: ${patlname} ${patfname}</p>
    
    <c:choose>
      <c:when test="${empty recordList}">
        <p class="error">処方された薬はありません。</p>
      </c:when>
      <c:otherwise>
        <table>
          <tr>
            <th>処方ID</th>
            <th>処方日</th>
            <th>薬剤ID</th>
            <th>薬剤名</th>
            <th>処方量</th>
          </tr>
          <c:forEach var="record" items="${recordList}">
            <tr>
              <td>${record.prescNo}</td>
              <td>${record.prescdate}</td>
              <td>${record.medicineid}</td>
              <td>${record.medicinename}</td>
              <td>${record.dosage}</td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>
    
    <br>
    <a href="mainPage.jsp">メインページに戻る</a>
  </body>
</html>
