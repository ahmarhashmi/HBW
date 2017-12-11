"use strict";

Dropzone.options.fileUploadForm = {
	maxFilesize : 20, // MB
	acceptedFiles : "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
	addRemoveLinks : true,
	autoProcessQueue : true,
	uploadMultiple : true,
	parallelUploads : 100,
	maxFiles : 100,

	success : function(file, response) {
		var imgName = response;
		file.previewElement.classList.add("dz-success");
		console.log("Successfully uploaded :" + file.name);
	},
	error : function(file, response) {
		file.previewElement.classList.add("dz-error");
	},

	init : function() {

		this.on('maxfilesexceeded', function(file) {
			this.removeFile(file);
		});
		
//		this.on('complete', async function(file){
//			await sleep(6000);
//			$('a.dz-remove').remove();
//		});
		
		/** This function is triggered when user attempts to delete a file */
		this.on('removedfile', async function(file){
			var CONTEXT_PATH = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
			
			//new Promise(resolve => setTimeout(resolve, ms));
			$.get(CONTEXT_PATH + "/FileUploadServlet?delete="+file.name, function(data) {});
		});

		var wrapperThis = this;

		this.on("addedfile", function(file) {
			var isTypeAllowed = file.type.match(/image.*/)
					|| file.type === 'application/pdf';

			if (!isTypeAllowed) {
				wrapperThis.removeFile(file);
				alert("File type not supported");
			}
			
			file.removeFile.addEventListener("click", function() {
				if( confirm("Remove "+file.name+"?") ){
					alert("removed");
				}
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
								var CONTEXT_PATH = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
								$("#image").attr('src', CONTEXT_PATH + "/FileUploadServlet?file="+file.name);
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

function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}
