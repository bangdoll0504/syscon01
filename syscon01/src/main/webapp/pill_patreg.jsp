<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>処方入力</title>
    <script type="text/javascript">
      function addMedicine() {
          var medSelect = document.getElementById("medicineSelect");
          var selectedValue = medSelect.value;
          if(selectedValue === "") return;
          var selectedText = medSelect.options[medSelect.selectedIndex].text;
          
          var dosage = document.getElementById("dosageInput").value;
          var adminCount = document.getElementById("adminInput").value;
          var days = document.getElementById("daysInput").value;
          if(dosage === "" || adminCount === "" || days === "") {
              alert("すべての数量を入力してください。");
              return;
          }
          
          var table = document.getElementById("medicineCart");
          var row = table.insertRow();
          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);
          
          cell1.innerHTML = selectedText;
          cell2.innerHTML = dosage;
          cell3.innerHTML = adminCount;
          cell4.innerHTML = days;
          
          // 薬剤IDのhidden
          var hiddenMed = document.createElement("input");
          hiddenMed.type = "hidden";
          hiddenMed.name = "selectedMedicine";
          hiddenMed.value = selectedValue;
          cell5.appendChild(hiddenMed);
          
          // 薬剤名のhidden
          var hiddenMedName = document.createElement("input");
          hiddenMedName.type = "hidden";
          hiddenMedName.name = "selectedMedicineName";
          hiddenMedName.value = selectedText;
          cell5.appendChild(hiddenMedName);
          
          // 1回あたりの投薬量のhidden
          var hiddenDosage = document.createElement("input");
          hiddenDosage.type = "hidden";
          hiddenDosage.name = "dosagePerAdmin";
          hiddenDosage.value = dosage;
          cell5.appendChild(hiddenDosage);
          
          // 1日あたりの投薬回数のhidden
          var hiddenAdmin = document.createElement("input");
          hiddenAdmin.type = "hidden";
          hiddenAdmin.name = "adminPerDay";
          hiddenAdmin.value = adminCount;
          cell5.appendChild(hiddenAdmin);
          
          // 投薬日数のhidden
          var hiddenDays = document.createElement("input");
          hiddenDays.type = "hidden";
          hiddenDays.name = "prescriptionDays";
          hiddenDays.value = days;
          cell5.appendChild(hiddenDays);
          
          var removeBtn = document.createElement("button");
          removeBtn.type = "button";
          removeBtn.innerHTML = "削除";
          removeBtn.onclick = function() {
              var rowIndex = this.parentNode.parentNode.rowIndex;
              table.deleteRow(rowIndex);
          };
          cell5.appendChild(removeBtn);
          
          // 入力欄をリセット
          document.getElementById("dosageInput").value = "0";
          document.getElementById("adminInput").value = "0";
          document.getElementById("daysInput").value = "0";
          medSelect.selectedIndex = 0;
      }
      
      // ショッピングカートから行を削除する汎用関数
      function removeRow(btn) {
          var row = btn.parentNode.parentNode;
          row.parentNode.removeChild(row);
      }
    </script>
  </head>
  <body>
    <h2>処方入力</h2>
    <!-- 患者情報の表示 -->
    <p>患者ID: ${patid}</p>
    <p>患者名: ${patfname} ${patlname}</p>
    
    <form action="PrescriptionController" method="post">
      <input type="hidden" name="patid" value="${patid}">
      
      <!-- 薬剤選択と数量入力 -->
      <p>
         処方薬の選択:
         <select id="medicineSelect">
           <option value="">-- 薬剤を選択 --</option>
           <c:forEach var="med" items="${medicineList}">
              <option value="${med.medicineid}">${med.medicinename} (${med.unit})</option>
           </c:forEach>
         </select>
      </p>
      <p>
         1回あたりの投薬量: 
         <input type="number" id="dosageInput" value="0" min="0" required>
      </p>
      <p>
         1日あたりの投薬回数: 
         <input type="number" id="adminInput" value="0" min="0" required>
      </p>
      <p>
         投薬日数: 
         <input type="number" id="daysInput" value="0" min="0" required>
      </p>
      
      <button type="button" onclick="addMedicine()">追加</button>
      
      <br><br>
      <!-- 既存のショッピングカート情報を再構築（投薬編集で戻った場合） -->
      <table id="medicineCart" border="1" cellpadding="5">
         <tr>
            <th>薬剤名</th>
            <th>1回あたりの投薬量</th>
            <th>1日あたりの投薬回数</th>
            <th>投薬日数</th>
            <th>操作</th>
         </tr>
         <c:if test="${not empty selectedMedicine}">
            <c:forEach var="medId" items="${selectedMedicine}" varStatus="status">
               <tr>
                  <td>${selectedMedicineName[status.index]}</td>
                  <td>${dosagePerAdmin[status.index]}</td>
                  <td>${adminPerDay[status.index]}</td>
                  <td>${prescriptionDays[status.index]}</td>
                  <td>
                     <button type="button" onclick="removeRow(this)">削除</button>
                     <!-- 隠しフィールドも再出力 -->
                     <input type="hidden" name="selectedMedicine" value="${medId}">
                     <input type="hidden" name="selectedMedicineName" value="${selectedMedicineName[status.index]}">
                     <input type="hidden" name="dosagePerAdmin" value="${dosagePerAdmin[status.index]}">
                     <input type="hidden" name="adminPerDay" value="${adminPerDay[status.index]}">
                     <input type="hidden" name="prescriptionDays" value="${prescriptionDays[status.index]}">
                  </td>
               </tr>
            </c:forEach>
         </c:if>
      </table>
      
      <br>
      <input type="submit" name="action" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
