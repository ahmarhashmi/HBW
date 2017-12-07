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

		var submitButton = document.querySelector("#submit-all");
		var wrapperThis = this;

		/*
		 * submitButton.addEventListener("click", function() {
		 * wrapperThis.processQueue(); });
		 */

		this.on("addedfile", function(file) {
			// alert(file.type=='application/pdf');

			var isTypeAllowed = file.type.match(/image.*/)
					|| file.type === 'application/pdf'

			if (!isTypeAllowed) {
				wrapperThis.removeFile(file);
				alert("File type not supported");
			}
			var src = document.getElementsByTagName("img").src;
			file.previewElement.addEventListener("click", function() {
				// alert(file.previewElement);

				$("#dialog").innerHTML = file.previewElement;
				$("#dialog").dialog(
						{
							closeOnEscape : true,
							modal : true,
							show : {
								effect : "blind",
								duration : 800
							},
							hide : {
								effect : "explode",
								duration : 1000
							},
							open : function(event, ui) {
								// $('#divInDialog').load('test.html',
								// function() {
								// alert('Load was performed.');
								// });
//								$(document).appendChild("<span id=''></span>")
								var CONTEXT_PATH = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
								alert(CONTEXT_PATH);
								
								$.get(CONTEXT_PATH + "/FileUploadServlet?file="+file.name,
										function(data) {
											$('#dialog').html("<img src="+data+" />");
											$('#dialog').dialog('open');
										}, "html");
							},
							title : "Dialog Title",
							buttons : [ {
								text : "Ok",
								icon : "ui-icon-heart",
								click : function() {
									$(this).dialog("close");
								}

							// Uncommenting the following line would hide the
							// text,
							// resulting in the label being used as a tooltip
							// showText: false
							} ]
						});
				// var win = window.open(src, '_blank');
				// win.focus();
			});

			// Create the remove button
			/*
			 * var removeButton = Dropzone .createElement("<button class='btn
			 * btn-lg dark'>Remove File</button>");
			 */
			// Listen to the click event
			/*
			 * removeButton .addEventListener( "click", function(e) { // Make
			 * sure the button click doesn't submit the form: e
			 * .preventDefault(); e .stopPropagation(); // Remove the file
			 * preview. wrapperThis .removeFile(file); // If you want to the
			 * delete the file on the server as well, // you can do the AJAX
			 * request here. });
			 */

			// Add the button to the file preview element.
			/*
			 * file.previewElement .appendChild(removeButton);
			 */
		});

		this.on('sendingmultiple', function(data, xhr, formData) {
			formData.append("Username", $("#Username").val());
		});
	}
};
