<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<title>Top Test</title>

	<script lang="javascript">
		var serviceMap ;
		function getServiceApi() {
			var href = "http://localhost:8090/apiTool";

			if (window.XMLHttpRequest) {
				var xmlHttpRequest = new XMLHttpRequest();
				xmlHttpRequest.open("GET", href, false);
				xmlHttpRequest.send(null);
				serviceMap = xmlHttpRequest.responseText;
				serviceMap = eval("(" + serviceMap + ")");
				document.getElementById("response_div").innerHTML = "aaaa";
			}
		}

		function dumpObject(object) {
			var output = '';
			for (property in object) {
				output += property + ': ' + object[property]+'; ';
			}
			return output;
		}

		function displayApiListByCategory(category) {
			apiList = serviceMap[category];
			var apiArray = new Array();
			document.getElementById("response_div").innerHTML = dumpObject(apiArray);
			for (var property in apiList) {

			}
		}

		function displayApiParams(apiName) {

		}

		function onServiceChange() {
			displayApiListByCategory();
		}
	</script>
</head>
<body onload="getServiceApi()">
<table>
	<tbody>
	<tr>
		<td>
			<table>
				<tbody>
				<tr>
					<td width="160" align="right">返回结果：</td>
					<td width="340">
						<select id="format" style="width:195px;" name="format">
							<option value="xml">XML</option>
							<option value="json">JSON</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="160" align="right">API类目：</td>
					<td width="340">
						<select id="apiCategoryId" onchange="displayApiListByCategory(this.value)" style="width:195px;" name="apiCategoryId">
							<option value="user_service">UserService</option>
							<option value="group_service">GroupService</option>
							<option value="item_service">ItemService</option>
							<option value="onsale_task_service">OnsaleTaskService</option>
							<option value="showcase_service">ShowcaseService</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="160" align="right">API：</td>
					<td width="340">
						<span id="sipApinameDiv">

						</span>
					</td>
				</tr>
				<tr>
					<td width="160" align="right">版本：</td>
					<td width="340">
						<input id="version" width="340" value="1.0">
					</td>
				</tr>
				<tr>
					<td width="160" align="right">Session：</td>
					<td width="340">
						<input id="session" width="340" value="">
					</td>
				</tr>
				<tr>
					<td width="160" align="right">Appkey：</td>
					<td width="340">
						<input id="appKey" width="340" value="sk_app_key">
					</td>
				</tr>
				<tr>
					<span id="paramDiv">

					</span>
				</tr>
				</tbody>
			</table>
		</td>
		<td>
			<span id="response_div">
				dfasd
			</span>
		</td>
	</tr>
	</tbody>
</table>

<!--
<div id="container">
<div id="sidebar">
<form id="param_form" action="http://localhost:8090/router" method="GET">
	<input id="param_name1" type="text" value="method"/>
	<select onchange="onServiceChange()">
		<option value="user_service">UserService</option>
		<option value="group_service">GroupService</option>
		<option value="item_service">ItemService</option>
		<option value="onsale_task_service">OnsaleTaskService</option>
		<option value="showcase_service">ShowcaseService</option>
	</select>
	<select id="param_value1">
		<option value="user.get">user.get</option>
		<option value="top.user.get">top.user.get</option>
		<option value="group.add">group.add</option>
		<option value="groups.get">groups.get</option>
		<option value="group.delete">group.delete</option>
		<option value="groups.import">groups.import</option>
		<option value="item.get">item.get</option>
		<option value="item.update">item.update</option>
		<option value="items.get">items.get</option>
		<option value="onsale.task.get">onsale.task.get</option>
		<option value="onsale.tasks.get">onsale.tasks.get</option>
		<option value="onsale.task.add">onsale.task.add</option>
		<option value="onsale.task.update">onsale.task.update</option>
		<option value="onsale.task.delete">onsale.task.update</option>
		<option value="showcase.get">showcase.get</option>
		<option value="showcase.update">showcase.update</option>
	</select>
	<br>

	<input id="param_name2" type="text" value="v"/><input id="param_value2" type="text" value="1.0"/> <br>
	<input id="param_name3" type="text" value="session"/><input id="param_value3" type="text"/> <br>
	<input id="param_name4" type="text" value="format"/><input id="param_value4" type="text" value="xml"/> <br>
	<input id="param_name5" type="text" value="appKey"/><input id="param_value5" type="text" value="sk_app_key"/> <br>
	<div>
		<input id="param_name6" type="text"/><input id="param_value6" type="text"/> <br>
		<input id="param_name7" type="text"/><input id="param_value7" type="text"/> <br>
		<input id="param_name8" type="text"/><input id="param_value8" type="text"/> <br>
		<input id="param_name9" type="text"/><input id="param_value9" type="text"/> <br>
		<input id="param_name10" type="text"/><input id="param_value10" type="text"/> <br>
		<input id="param_name11" type="text"/><input id="param_value11" type="text"/> <br>
		<input id="param_name12" type="text"/><input id="param_value12" type="text"/> <br>
		<input id="param_name13" type="text"/><input id="param_value13" type="text"/> <br>
		<input type="button" value="Send" onclick="send()">
	</div>
</form>
</div>
<div id="content">
	<xmp id="response_div">

	</xmp>
</div>
</div>
-->
</body>
</html>