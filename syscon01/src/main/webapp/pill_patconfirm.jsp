<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>処方確認</title>
    <style>
      table { border-collapse: collapse; width: 100%; }
      th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
  </head>
  <body>
    <h2>処方確認</h2>
    <p>患者ID: ${param.patid}</p>
    <p>患者名: ${param.patfname} ${param.patlname}</p>
    <p>共通数量:</p>
    <ul>
      <li>1回あたりの投薬量: ${param.dosagePerAdmin}</li>
      <li>1日あたりの投薬回数: ${param.adminPerDay}</li>
      <li>投薬日数: ${param.prescriptionDays}</li>
    </ul>
    
    <h3>処方薬一覧</h3>
    <table>
      <tr>
         <th>薬剤ID</th>
         <th>薬剤名</th>
         <th>1回あたりの投薬量</th>
         <th>1日あたりの投薬回数</th>
         <th>投薬日数</th>
      </tr>
      <c:forEach var="med" items="${paramValues.selectedMedicine}" varStatus="status">
        <tr>
          <td>${med}</td>
          <td>${paramValues.selectedMedicineName[status.index]}</td>
          <td>${paramValues.dosagePerAdmin[status.index]}</td>
          <td>${paramValues.adminPerDay[status.index]}</td>
          <td>${paramValues.prescriptionDays[status.index]}</td>
        </tr>
      </c:forEach>
    </table>
    
    <form action="PrescriptionController" method="post">
      <!-- 共通情報 -->
      <input type="hidden" name="patid" value="${param.patid}">
      <!-- 薬剤ID と 薬剤名、各数量の hidden 項目 -->
      <c:forEach var="med" items="${paramValues.selectedMedicine}">
         <input type="hidden" name="selectedMedicine" value="${med}">
      </c:forEach>
      <c:forEach var="name" items="${paramValues.selectedMedicineName}">
         <input type="hidden" name="selectedMedicineName" value="${name}">
      </c:forEach>
      <c:forEach var="dos" items="${paramValues.dosagePerAdmin}">
         <input type="hidden" name="dosagePerAdmin" value="${dos}">
      </c:forEach>
      <c:forEach var="adm" items="${paramValues.adminPerDay}">
         <input type="hidden" name="adminPerDay" value="${adm}">
      </c:forEach>
      <c:forEach var="day" items="${paramValues.prescriptionDays}">
         <input type="hidden" name="prescriptionDays" value="${day}">
      </c:forEach>
      
      <input type="submit" name="action" value="処方">
      <input type="submit" name="action" value="投薬編集">
      <input type="submit" name="action" value="キャンセル">
    </form>
  </body>
</html>
