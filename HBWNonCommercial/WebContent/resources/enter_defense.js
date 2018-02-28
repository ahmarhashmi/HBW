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
		this.on('removedfile', function(file){
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

		this.on("addedfile", function(file) {
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
            file._captionLabel = Dropzone.createElement("<div class='delconfirmation'><div class='captionfiledel'>Remove "+file.name+"? </div><div class='deletebuttons'></div></div>")
            
            var delbutton = Dropzone.createElement("<input id='"+file.name+"_del' type='button' name='"+file.name+"_del' class='deletebutton' value='Remove' />");
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

//function sleep(ms) {
//	  return new Promise(resolve => setTimeout(resolve, ms));
//	}

function getContextPath(){
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function cancelRequestConfirmationDialog(message) {
    $('<div></div>').appendTo('body')
        .html('<div><h6>'+message+'?</h6></div>')
        .dialog({
            modal: true, title: 'Cancel Hearing Request?', zIndex: 10000, autoOpen: true,
            width: 'auto', resizable: false,
            buttons: {
            	"Cancel Request": function () {
                    $(window).unbind('beforeunload');
            		startAfresh();		
            		$(this).remove();
                },
                Stay: function () {                                                                 
                    $(this).remove();
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


function displaySummonsImage(ViewSummons, encodedVioNumber)
{
      var summonsURL = "http://nycserv.nyc.gov/NYCServWeb/ShowImage?searchID=" + encodedVioNumber + "&locationName=Commercial Collections";
      var windowprops = "location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes,resizable=1,left=" + 25 + ",top = "+ 25 + ",width=" + 800 + ",height=" + 700;
      var image_window = window.open(summonsURL, "Ticket_Image_Screen",windowprops);
      image_window.focus();
}

