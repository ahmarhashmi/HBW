<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>jQuery File Upload Example</title>
<script src="${pageContext.request.contextPath}/js/jquery.1.9.1.min.js"></script>

<script src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.fileupload.js"></script>

<!-- bootstrap just to have good looking page -->
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />

<!-- we code these -->
<link href="${pageContext.request.contextPath}/css/dropzone.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/myuploadfunction.js"></script>
</head>

<body>
<h1>Spring MVC - jQuery File Upload</h1>
<div style="width:500px;padding:20px">

<%-- <s:form action="/multiUploadFile" enctype="multipart/form-data" namespace="/upload" method="post">
            <s:file name="uploads" multiple="multiple" id="fileupload"/>
            <s:submit value="Upload files" />
        </s:form> --%>

	<s:file id="fileupload" name="uploads" type="file" data-url="multiUploadFile" multiple />
	
	<div id="dropzone" class="fade well">Drop files here</div>
	
	<div id="progress" class="progress">
    	<div class="bar" style="width: 0%;"></div>
	</div>

	<table id="uploaded-files" class="table">
		<tr>
			<th>File Name</th>
			<th>File Size</th>
			<th>File Type</th>
			<th>Download</th>
		</tr>
	</table>
	
</div>
</body> 
</html>
