"use strict";

var NOACTION = false;

// var files = [];
var virusFreeFiles = [];
var infectedFiles = [];

Dropzone.options.fileUploadForm = {
	maxFilesize : 20, // MB
	acceptedFiles : "image/gif,image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
	addRemoveLinks : true,
	dictRemoveFileConfirmation: true,
	autoProcessQueue : true,
	uploadMultiple : true,
	parallelUploads : 1,
	dictInvalidFileType : "File type not supported.",
	maxFiles : 100,

	success : function(file, response) {
		var imgName = response;
		file.previewElement.classList.add("dz-success");
		$('#affirm').attr("checked", false);
		$('#affirmCheckBox').css("display", "none");
		$('#affirmCheckBoxPrompt').css("display", "none");
	},
	error : function(file, response) {
		file.previewElement.classList.add("dz-error");
		$(file.previewElement).find('.dz-error-message span').text(response.replace('Error 500: ',''));
		var errorMessage = $(file.previewElement).find('.dz-error-message span');
		console.log(errorMessage);
		if( response.match(/Session timed out*/)){
			$(window).unbind('beforeunload');
    		startAfresh();
		}
	},
	
	init : function() {
		this.on('maxfilesexceeded', function(file) {
			this.removeFile(file);
			enableDisableSubmitButton();
		});
		
		this.on('success', function(file) {
// files.push(file);
			enableDisableSubmitButton();
		});
		
		/** This function is triggered when user attempts to delete a file */
		this.on('removedfile', async function(file){
			$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
			/*
			 * var index = files.indexOf(file); if (index > -1) {
			 * files.splice(index, 1); }
			 */
			if(this.getAcceptedFiles().length == 0){
				$('#affirmCheckBox').css("display", "block");
				$('#affirmCheckBoxPrompt').css("display", "block");
			}
			$("#displayError").empty();
			enableDisableSubmitButton();
		});

		var wrapperThis = this;

		this.on("addedfile", async function(file) {
			/* var dataId = */
			/*
			 * scanForVirus(file, wrapperThis);
			 * 
			 * console.log("ScanCompleted: "+scanComplete);
			 * while(!scanComplete){ // console.log("wait start: "); // await
			 * sleep(15000); var counter = 0;
			 * 
			 * for(var i=0;i<10;i++) { console.log(counter++ + "i=" + i +
			 * "scanComplete in while for = " + scanComplete); if(scanComplete)
			 * break; } } console.log("ScanCompleted after while:
			 * "+scanComplete); if( scanComplete ){
			 */
			var caption = file.caption == undefined ? "" : file.caption;
            file._captionLabel = Dropzone.createElement("<div class='delconfirmation'><div class='captionfiledel'>Delete "+file.name+"? </div><div class='deletebuttons'></div></div>")
            
            var delbutton = Dropzone.createElement("<input id='"+file.name+"_del' type='button' name='"+file.name+"_del' class='deletebutton' value='Delete' />");
            var cancelbutton = Dropzone.createElement("<input id='"+file.name+"_can' type='button' name='"+file.name+"_can' class='cancelbutton' value='Cancel' />");
            var removeExceptionbutton = Dropzone.createElement("<input id='"+file.name+"_exp' type='button' name='"+file.name+"_exp' class='expbutton' value='Exp' />");
            var clearfixdiv = Dropzone.createElement("<div class='clearfix'></div>");

				delbutton.onclick = function () {
					wrapperThis.removeFile(file);
					NOACTION = true;
				};
              
		       cancelbutton.onclick = function () {
		    	   file.previewElement.classList.remove("dz-delete-cofirmation");
		    	   NOACTION = true;
		       };
               
	           removeExceptionbutton.onclick = function () {
	               file.previewElement.remove();            	
	               NOACTION = true;
	           };
                  
	          file.previewElement.appendChild(file._captionLabel);
	          file.previewElement.appendChild(delbutton);
	          file.previewElement.appendChild(cancelbutton);
	          file.previewElement.appendChild(removeExceptionbutton);
	          file.previewElement.appendChild(clearfixdiv);
                  
            // file.previewElement.appendChild(file._captionBox);
			var isTypeAllowed = file.type.match(/image.*/) || file.type === 'application/pdf';

			if (!isTypeAllowed) {
				wrapperThis.removeFile(file);
				alert("File type not supported");
			}
			
			file.previewElement.addEventListener('click', function() {
				if(file.status == 'success' && !NOACTION){					
				$("#dialog").innerHTML = file.previewElement;
				$("#dialog").dialog({
					width: 800,
					height: 600,
					closeOnEscape : true,
					modal : true,
					show : {
						effect : "blind",
						duration : 1000
					},
					hide : {
						effect : "explode",
						duration : 800
					},
					open : function(event, ui) {
						var expression = /pdf/;
						if( file.name.match(expression)){
							$("#image").hide();
							$("#frame").show();
							$("#frame").attr("src", getContextPath() + "/FileUploadServlet?file="+file.name);
						} else{
							$("#frame").hide();
							$("#image").show();
							$("#image").attr('src', getContextPath() + "/FileUploadServlet?file="+file.name);
						}
					},
					title : file.name,
					buttons : [ {
						text : "Ok",
						icon : "ui-icon-heart",
						click : function() {
							$(this).dialog("close");
						}
					} ]
					});
				}
				NOACTION = false;
			
			});
			// }
		});

		this.on('sendingmultiple', function(data, xhr, formData) {
			formData.append("Username", $("#Username").val());
		});
	}/*
		 * , accept: function (file, done) { alert('hi'+ file.name); if
		 * (file.name == "justinbieber.jpg") { done("Naha, you don't."); } }
		 */
};

function setDefenseValue(obj, errorMessageSpanId) {
	if(errorMessageSpanId == "enterDefenseMessage"){
		document.getElementById("defenseHidden").value = obj.value;
	}else{
		document.getElementById("explainWhyHidden").value = obj.value;
	}

	if (obj.value.length >= 32700) {
		document.getElementById(errorMessageSpanId).style.display = "block";
	} else if (obj.value.length < 32700) {
		document.getElementById(errorMessageSpanId).style.display = "none";
	}
}

function setViolationNumber() {
	document.getElementById("violationHidden").value = document
			.getElementById("violationNumber").innerHTML;
}

function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}

function getContextPath(){
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function cancelRequest(){
	ConfirmDialog("You will lose any information and files entered into this form.");
//	if(confirm("You will lose any information and files entered into this form.")){
//		$(window).unbind('beforeunload');
//		startAfresh();		
//	}
}


function ConfirmDialog(message) {
    $('<div></div>').appendTo('body')
        .html('<div><h6>'+message+'?</h6></div>')
        .dialog({
            modal: true, title: 'Cancel Hearing Request?', zIndex: 10000, autoOpen: true,
            width: 'auto', resizable: false,
            buttons: {
            	"Cancel Request": function () {
                    $(window).unbind('beforeunload');
            		startAfresh();		
//                                $(this).dialog("close");
            		$(this).remove();
                },
                Stay: function () {                                                                 
                    $(this).remove();
                   // $(this).dialog("close");
                }
            },
            close: function (event, ui) {
                $(this).remove();
            }
        });
};

function startAfresh(){
	$.get(getContextPath() + "/FileUploadServlet?reset=true", function(data) {});
	window.location='ticket/broker_selection.jsp';
}

/**
 * submits the file to the cloud and where the file is scanned for virus.
 * 
 * @param file
 * @param index
 * @returns
 */
async function scanForVirus(file, index){
	
	// console.log("Calling virus scan for the file: "+file.name);
	var data = new FormData();
	data = file;
	jQuery.ajax({
	    url: 'https://scan.metadefender.com/v2/file',
	    data: data,
	    cache: false,
	    contentType: false,
	    processData: false,
	    method: 'POST',
	    type: 'POST', // For jQuery < 1.9
	    async:false,
	    headers: {
			apikey: '7d528a65300a6865769b91df10d5822d'
		},
	    success: function(data){
	    	var dataId = data.data_id;
	    	// console.log("DataID returned is:" +dataId);
	    	containsVirus(data.data_id, index, file);
	    }
	});
}

/**
 * Queries the remote cloud server to see if the file that was submitted for
 * virus scanning is complete and about the result/report
 * 
 * @param dataId
 * @param index
 * @returns
 */
async function containsVirus(dataId, index, file){
	
	var scanPercentage = 0;
	while(scanPercentage < 100){
		// console.log("Checking status of the image having dataId: "+dataId)
		jQuery.ajax({
		    url: 'https://api.metadefender.com/v2/file/'+dataId,
		    cache: false,
		    method: 'GET',
		    type: 'GET', // For jQuery < 1.9
		    async: false,
		    headers: {
				apikey: '7d528a65300a6865769b91df10d5822d'
			},
		    success: function(data){
		    	console.log(data);
		    	scanPercentage = data.scan_results.progress_percentage;
		    	var scanResult = data.scan_results.scan_all_result_a;
		    	// console.log("Progress percentage: "+scanPercentage);
		    	// console.log("Scan result: "+scanResult);
		    	if( scanPercentage == 100 && scanResult == "No threat detected"){
		    		virusFreeFiles.push(index);
		    		// infectedFiles.push(file.name);
		    		// console.log("'"+file.name+"' is clean and does not
					// contain virus.");
					return;		    		
				} else if (scanPercentage == 100 && scanResult != "No threat detected"){
					$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
					infectedFiles.push(file.name);
		    		// console.log("'"+file.name+"' contains virus.");
		    		return;
		    	} 
		    }
		});
	}
}


function isAllUploadedFilesClean(){
	infectedFiles = [];
	virusFreeFiles = [];
	
	// $("#loadingDiv").show();
	files.forEach( scanForVirus );
	console.log("Total files count :"+files.length);
	console.log("Infected files count :"+infectedFiles.length);
	console.log("Clean files count :"+virusFreeFiles.length);
	// $("#loadingDiv").hide();
	return infectedFiles;
}

function enableDisableSubmitButton(onPageload){
	
	var dz = Dropzone.forElement("#file-upload-form"); 
	// alert(dz.getAcceptedFiles());
	// dz.processFiles(dz.files);
	var isValid = true;
	
	var isInJudgment = $('#explainWhyID');
	if( isInJudgment.is(':visible') && !isInJudgment.val() ){
		isValid = false;
	}
	if(!$('#enterDefenseID').val()){
		isValid = false;
	}

	if (!$('#certify')[0].checked) {
		isValid = false;
	} 
	if (!$('#firstName').val()) {
		isValid = false;
	}
	if (!$('#lastName').val()) {
		isValid = false;
	}
	if (!$('#address').val()) {
		isValid = false;
	}
	if (!$('#city').val()) {
		isValid = false;
	}
	if ($('#state').val() == 1) {
		isValid = false;
	}
	if (!$('#zip').val()) {
		isValid = false;
	}
	if (!$('#email1').val()) {
		isValid = false;
	}
	if (!$('#email2').val()) {
		isValid = false;
	}
	if ($('#email1').val() != $('#email2').val()) {
		isValid = false;
	}
	if( !isValidEmail('#email1').val()) ){
		$("#email1FormatMsg").show();
		isValid = false;
	} else{
		$("#email1FormatMsg").hide();
	}
	if ($('#affirm').is(":visible")
			&& !$('#affirm')[0].checked) {
		if(!onPageload){
			$('#affirmMsg').css("color", "red");
		}
		isValid = false;
	} else {
		$('#affirmMsg').css("color", "black");
	}

	if (isValid) {
		$("#submitBtn").removeAttr('disabled');
	} else{
		$("#submitBtn").attr("disabled", true);
	}
}

function format_Image_Page(ticket_num, popupType, targetURL) {
	var agt = navigator.userAgent.toLowerCase();
	var serviceName = " ";
	if (popupType == "pdf") {
		serviceName = "GET_VIO_IMAGE_PDF";
	} else {
		serviceName = "GET_IMAGE_FILEPATH";
	}

	var csshref = '';
	if ((agt.indexOf("win") != -1) || (agt.indexOf("16bit") != -1)) {
		if (agt.indexOf('msie') != -1) {
			csshref = '<LINK href="nycserv_pc_ie.css" rel="stylesheet" type="text/css">';
		} else {
			csshref = '<LINK href="nycserv_pc_net.css" rel="stylesheet" type="text/css">';
		}
	} else {
		if (agt.indexOf('msie') != -1) {
			csshref = "<LINK href='nycserv_mac_ie.css' rel='stylesheet' type='text/css'>";
		} else {
			csshref = '<LINK href="nycserv_mac_net.css" rel="stylesheet" type="text/css">';
		}
	}

	var page_text = "<html>\n"
			+ "<head>\n"
			+ "<title>Ticket Image </title>\n"
			+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"
			+ "\n";

	var page_text2 = "\n"
			+ "<!-----Script  --->\n"
			+ "<script src=\"scripts/navigation.js\" language=\"Javascript\"></script>\n"
			+ "<script src=\"scripts/protocol.js\" language=\"Javascript\"></script>\n"
			+ "<!-----End of Script  --->\n"
			+ "</head>\n"
			+ "\n"
			+ "<body bgcolor=\"#FFFFFF\" alink=\"#0000FF\" vlink=\"#800080\" onload=\"submitProtocolForm()\">\n"
			+ "<center><br><br><br><br><br><br><br><br><br>Retrieving Image - Please Wait .......... </center>\n"
			+ "\n";

	page_text2 += "    \n"
			+ "<form NAME='NycservProtocolForm' METHOD=POST action='"
			+ targetURL + "'> \n"
			+ "<INPUT TYPE=HIDDEN NAME=ChannelType VALUE=ct/Browser> \n"
			+ "<INPUT TYPE=HIDDEN NAME=RequestType VALUE=rt/Business> \n"
			+ "<INPUT TYPE=HIDDEN NAME=SubSystemType VALUE=st/Payments> \n"
			+ "<INPUT TYPE=HIDDEN NAME=AgencyType VALUE=at/ALL> \n"
			+ "<INPUT TYPE=HIDDEN NAME=ServiceName VALUE=" + serviceName
			+ "> \n" + "<INPUT TYPE=HIDDEN NAME=MethodName VALUE=NONE> \n"
			+ "<INPUT TYPE=HIDDEN NAME=PageID VALUE=Violations_Image> \n"
			+ "<INPUT TYPE=HIDDEN NAME=ParamCount VALUE=0> \n"
			+ "<INPUT TYPE=HIDDEN NAME=NycservRequest VALUE=EMPTY> \n"
			+ "<INPUT TYPE=HIDDEN NAME=VIOLATION_NUMBER VALUE=" + ticket_num
			+ "> \n" + "</form> <!-- end of form--> \n" + "\n";

	page_text2 += "    \n" + "</body>\n" + "</html>\n" + "\n";

	var page_text3 = page_text + csshref + page_text2;
	return page_text3;

}

function format_Image_Page(ticket_num, popupType, targetURL, issue_date) {
	var agt = navigator.userAgent.toLowerCase();
	var serviceName = " ";
	if (popupType == "pdf") {
		serviceName = "GET_VIO_IMAGE_PDF";
	} else {
		serviceName = "GET_IMAGE_FILEPATH";
	}

	var csshref = '';
	if ((agt.indexOf("win") != -1) || (agt.indexOf("16bit") != -1)) {
		if (agt.indexOf('msie') != -1) {
			csshref = '<LINK href="${pageContext.request.contextPath}/css/nycserv_pc_ie.css" rel="stylesheet" type="text/css">';
		} else {
			csshref = '<LINK href="${pageContext.request.contextPath}/css/nycserv_pc_net.css" rel="stylesheet" type="text/css">';
		}
	} else {
		if (agt.indexOf('msie') != -1) {
			csshref = "<LINK href='${pageContext.request.contextPath}/css/nycserv_mac_ie.css' rel='stylesheet' type='text/css'>";
		} else {
			csshref = '<LINK href="${pageContext.request.contextPath}/css/nycserv_mac_net.css" rel="stylesheet" type="text/css">';
		}
	}

	var page_text = "<html>\n"
			+ "<head>\n"
			+ "<title>Ticket Image </title>\n"
			+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"
			+ "\n";

	var page_text2 = "\n"
			
			  // + "<!-----Script --->\n"
			  // + "<script src=\"scripts/navigation.js\"
				// language=\"Javascript\"></script>\n"
			  // + "<script src=\"scripts/protocol.js\"
				// language=\"Javascript\"></script>\n"
			  // + "<!-----End of Script --->\n"
			 
		+"<script type='text/javascript'>"
		+ "function submitProtocolForm (){\n"
		+"var theForm = getProtocolForm();\n"
		+"theForm.submit();\n"
		+"return true;\n"
		+"}\n"
		
		+"function getProtocolForm (){\n"
		+"if (navigator.appName == 'Netscape'){\n"
		+"if (document.forms[0] == undefined)\n"
		+"return document.outer.document.forms['NycservProtocolForm'];\n"
		+"else\n"
		+"return document.forms['NycservProtocolForm'];\n"
		+"} else {\n"
		+"return document.forms['NycservProtocolForm'];\n"
		+"}\n"
		+"}\n"
		+"</script>"
			+ "</head>\n"
			+ "\n"
			+ "<body bgcolor=\"#FFFFFF\" alink=\"#0000FF\" vlink=\"#800080\" onload=\"submitProtocolForm()\">\n"
			+ "<center><br><br><br><br><br><br><br><br><br>Retrieving Image - Please Wait .......... </center>\n"
			+ "\n";

	page_text2 += "    \n"
		+ "<form NAME='NycservProtocolForm' METHOD=POST action='"
		+ targetURL + "'> \n"
		+ "<INPUT TYPE=HIDDEN NAME=ChannelType VALUE=ct/Browser> \n"
		+ "<INPUT TYPE=HIDDEN NAME=RequestType VALUE=rt/Business> \n"
		+ "<INPUT TYPE=HIDDEN NAME=SubSystemType VALUE=st/Payments> \n"
		+ "<INPUT TYPE=HIDDEN NAME=AgencyType VALUE=at/ALL> \n"
		+ "<INPUT TYPE=HIDDEN NAME=ServiceName VALUE=" + serviceName
		+ "> \n" + "<INPUT TYPE=HIDDEN NAME=MethodName VALUE=NONE> \n"
		+ "<INPUT TYPE=HIDDEN NAME=PageID VALUE=Violations_Image> \n"
		+ "<INPUT TYPE=HIDDEN NAME=ParamCount VALUE=0> \n"
		+ "<INPUT TYPE=HIDDEN NAME=NycservRequest VALUE=EMPTY> \n"
		+ "<INPUT TYPE=HIDDEN NAME=VIOLATION_NUMBER VALUE=" + ticket_num
		+ "> \n" + "<INPUT TYPE=HIDDEN NAME=VIOLATION_ISSUE_DATE VALUE="
		+ issue_date + "> \n" + "</form> <!-- end of form--> \n" + "\n";
	 
	/*
	 * page_text2 +="\n" +"<img src='../images/ticket_sample.jpg'/>"
	 */

	page_text2 += "    \n" + "</body>\n" + "</html>\n" + "\n";

	var page_text3 = page_text + csshref + page_text2;
	return page_text3;

}
