"use strict";

var NOACTION = false;

var files = [];
var virusFreeFiles = [];
var infectedFiles = [];

Dropzone.options.fileUploadForm = {
	maxFilesize : 20, // MB
	acceptedFiles : "image/gif,image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
	addRemoveLinks : true,
	dictRemoveFileConfirmation: true,
	autoProcessQueue : true,
	uploadMultiple : true,
	parallelUploads : 100,
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
		$(file.previewElement).find('.dz-error-message span').text(response);
	},
	
	init : function() {
		this.on('maxfilesexceeded', function(file) {
			this.removeFile(file);
		});
		
		this.on('success', function(file) {
			files.push(file);
		});
		
		/** This function is triggered when user attempts to delete a file */
		this.on('removedfile', async function(file){
			$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
			var index = files.indexOf(file);
			if (index > -1) {
			    files.splice(index, 1);
			}
			if(this.getAcceptedFiles().length == 0){
				$('#affirmCheckBox').css("display", "block");
				$('#affirmCheckBoxPrompt').css("display", "block");
			}
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
				if(!NOACTION){					
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
	}
};

function setDefenseValue(obj) {
	document.getElementById("defenseHidden").value = obj.value;

	if (obj.value.length >= 32700) {
		document.getElementById("maxLengthReached").style.display = "block";
	} else if (obj.value.length < 32700) {
		document.getElementById("maxLengthReached").style.display = "none";
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
	if(confirm("You will lose any information and files entered into this form.")){
		$(window).unbind('beforeunload');
		startAfresh();		
	}
}

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
	
	console.log("Calling virus scan for the file: "+file.name);	
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
	    	console.log("DataID returned is:" +dataId);
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
		console.log("Checking status of the image having dataId: "+dataId)
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
		    	console.log("Progress percentage: "+scanPercentage);
		    	console.log("Scan result: "+scanResult);
		    	if( scanPercentage == 100 && scanResult == "No threat detected"){
		    		virusFreeFiles.push(index);
		    		//infectedFiles.push(file.name);
		    		console.log("'"+file.name+"' is clean and doesn not contain virus.");
					return;		    		
				} else if (scanPercentage == 100 && scanResult != "No threat detected"){
					$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
					infectedFiles.push(file.name);
		    		console.log("'"+file.name+"' contains virus.");
		    		return;
		    	} 
		    }
		});
	}
}


function isAllUploadedFilesClean(){
	infectedFiles = [];
	virusFreeFiles = [];
	
	//$("#loadingDiv").show();
	files.forEach( scanForVirus );
	console.log("Total files count :"+files.length);
	console.log("Infected files count :"+infectedFiles.length);
	console.log("Clean files count :"+virusFreeFiles.length);
	//$("#loadingDiv").hide();
	return infectedFiles;
}
