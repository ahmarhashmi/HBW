"use strict";

// function callMyAction() {
// $.ajax({
// type : "GET",
// url : getContextPath()" + "/FileUploadServlet?delete="+file.name",
// success : function(itr) {
// var x = "<ol>";
// $.each(itr.dataList, function() {
// x += "<li>" + this + "</li>";
// });
// x += "</ol>";
// $("#websparrow").html(x);
// },
// error : function(itr) {
// alert("No values found..!!");
// }
// });
// }
var filesCount = 0;

Dropzone.options.fileUploadForm = {
	maxFilesize : 20, // MB
	acceptedFiles : "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
	addRemoveLinks : true,
	dictRemoveFileConfirmation: true,
	autoProcessQueue : true,
	uploadMultiple : true,
	parallelUploads : 100,
	dictInvalidFileType : "File type not supported.",
	maxFiles : 100,

	success : function(file, response) {
		filesCount = this.files.length;
		var imgName = response;
		file.previewElement.classList.add("dz-success");
		$('#affirm').attr("checked", false);
		$('#affirmCheckBox').css("display", "none");
		$('#affirmCheckBoxPrompt').css("display", "none");
	},
	error : function(file, response) {
		file.previewElement.classList.add("dz-error");
	},
	
	init : function() {

		this.on('maxfilesexceeded', function(file) {
			this.removeFile(file);
		});
		
// this.on('complete', async function(file){
// await sleep(6000);
// $('a.dz-remove').remove();
// });
		
		/** This function is triggered when user attempts to delete a file */
		this.on('removedfile', async function(file){
			$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
			if(this.getAcceptedFiles().length == 0){
				$('#affirmCheckBox').css("display", "block");
				$('#affirmCheckBoxPrompt').css("display", "block");
			}
		});

		var wrapperThis = this;

		this.on("addedfile", function(file) {
			var isTypeAllowed = file.type.match(/image.*/)
					|| file.type === 'application/pdf';

			if (!isTypeAllowed) {
				wrapperThis.removeFile(file);
				alert("File type not supported");
			}
			
			file.previewElement.addEventListener("click", function() {
				$("#dialog").innerHTML = file.previewElement;
				$("#dialog").dialog(
						{
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
								$("#image").attr('src', getContextPath() + "/FileUploadServlet?file="+file.name);
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
			});
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

