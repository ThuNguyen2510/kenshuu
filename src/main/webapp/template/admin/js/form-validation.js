$(document).ready(function () {
	$("form[name='registration']").validate(
			{
				// Specify validation rules
				rules : {
					userId : {
						required : true,
						maxlength : 8,

					},
					firstName : {
						required : true,
						maxlength : 10,

					},

					familyName : {
						required : true,
						maxlength : 10,

					},
					password : {
						required : true,
						maxlength : 8,

					},
					age : {
						digits : true,
					}
				},
				messages : {
					userId : {
						required : "※ユーザーIDが未入力です。",
						maxlength : "※ユーザーIDは8文字以下。"
					},
					firstName : {
						required : "※名がが未入力です。",
						maxlength : "※名は10文字以下。",
					},

					familyName : {
						required : "※姓がが未入力です。",
						maxlength : "※姓は10文字以下。",
					},
					password : {
						required : "※パスワードが未入力です。",
						maxlength : "※パスワードは8文字以下。",
					},
					age : {
						digits : "※年齢は整数です。"
					}
				},
				 errorElement : 'h3',
				 errorLabelContainer: '.errorTxt'
			});
});