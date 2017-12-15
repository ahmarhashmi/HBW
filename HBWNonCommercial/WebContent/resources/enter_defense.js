"use strict";

function callMyAction() {
	$.ajax({
		type : "GET",
		url : "/create_hearing",
		traditional: true,
		success : function(itr) {
			var x = "<ol>";
			$.each(itr.dataList, function() {
				x += "<li>" + this + "</li>";
			});
			x += "</ol>";
			$("#websparrow").html(x);
		},
		error : function(itr) {
			alert("No values found..!!");
		}
	});
}

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
		
// this.on('complete', async function(file){
// await sleep(6000);
// $('a.dz-remove').remove();
// });
		
		/** This function is triggered when user attempts to delete a file */
		this.on('removedfile', async function(file){
			$.get(getContextPath() + "/FileUploadServlet?delete="+file.name, function(data) {});
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

function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}

function getContextPath(){
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

$(document).ready( function() {
	$.subscribe('removeErrors', function(event,data) {
		$('.errorLabel').html('').removeClass('errorLabel');
		$('#formerrors').html('');
	});
});	

function customValidation(form, errors) {
	
	//List for errors
	var list = $('#formerrors');
	
	//Handle non field errors 
	if (errors.errors) {
		$.each(errors.errors, function(index, value) { 
			list.append('<<li>'+value+'</li>\n');
		});
	}
	
	//Handle field errors 
	if (errors.fieldErrors) {
		$.each(errors.fieldErrors, function(index, value) { 
			var elem = $('#'+index+'Error');
			if(elem)
			{
				elem.html(value[0]);
				elem.addClass('errorLabel');
			}
		});
	}
}
