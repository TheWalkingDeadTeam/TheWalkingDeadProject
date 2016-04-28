$( document ).ready(function() {
	// popup-------------------------------------------------------------------------------popup
		$('.regbut').bind('click', function(){
			$('.registration').fadeIn(500); //openpopup
			openValidate();
		});

		$('.closebtn').bind('click', function(){
			$('.registration').fadeOut(300); //closebutton
		});

		$('.layout').bind('click', function(){
			$('.registration').fadeOut(300); //layoutclose
		});
	//popup
	// -------------------------------------------------------------------------------validation
		$('.form-control').bind('input', ValidateForm);
		// binding


		function ValidateForm(){
				var elem = $(this);
				var innerText = elem.val();
				var errorMsg = '';

				if(elem.is('#name')){

					if( !/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText) ){
						errorMsg = errorMsg + 'Incorrect name';
					}else if(/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect name';
					}else if(!/^.{2,30}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect name';
					};

					$('.correct-name').text(errorMsg);
				}else if(elem.is('#email')){

					if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect email';
					}

					$('.correct-email').text(errorMsg);	
				}else if(elem.is('#password')){

					if (!/^.{6,20}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect symbols number';
					}

					$('.correct-password').text(errorMsg);
				}else if(elem.is('#surename')){

					if( !/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText) ){
						errorMsg = errorMsg + 'Need a capital letter;';
					};

					if(/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)){
						errorMsg = errorMsg + 'Only letter\'s;';
					};

					if(!/^.{2,30}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect letter\'s number;';
					};
					$('.correct-surename').text(errorMsg);
				};
				buttonEnable();
		};
		// oninputvalidation

		function openValidate(){
				var errorMsg = '';

				var elem = $('#name');
				var innerText = elem.val();

					if( !/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText) ){
						errorMsg = errorMsg + 'Incorrect name';
					}else if(/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect name';
					}else if(!/^.{2,30}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect name';
					};

					$('.correct-name').text(errorMsg);
					errorMsg = '';

				var elem = $('#email');
				var innerText = elem.val();

					if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect email';
					}

					$('.correct-email').text(errorMsg);
					errorMsg = '';
			
				var elem = $('#password');
				var innerText = elem.val();

					if (!/^.{6,20}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect symbols number';
					}

					$('.correct-password').text(errorMsg);
					errorMsg = '';

				var elem = $('#surename');
				var innerText = elem.val();

					if( !/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText) ){
						errorMsg = errorMsg + 'Need a capital letter;';
					};

					if(/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)){
						errorMsg = errorMsg + 'Only letter\'s;';
					};

					if(!/^.{2,30}$/.test(innerText)){
						errorMsg = errorMsg + 'Incorrect letter\'s number;';
					};
					$('.correct-surename').text(errorMsg);

					buttonEnable();
		};
		// start validation
		function buttonEnable(){
			if($('.correct-name').text() == '' && $('.correct-password').text() == '' && $('.correct-email').text() == '' && $('.correct-surename').text() == ''){
				$('#user button').prop('disabled', false);
			}else{
				$('#user button').prop('disabled', true);
			}
		};
	// ------------------------------------------validation


	$("#buttonRegistration").click(function () {
		event.preventDefault();
		$.ajax({
			type: 'post',
			url: '/register',
			dataType: 'json',
			contentType: "application/json",
			data: JSON.stringify({
				name: $('#name').val(),
				surname: $('#surname').val(),
				email: $('#email').val(),
				password: $('#password').val()
			}),
			success: function (response) {
				if (response.errors.length) {
					response.errors.forEach(item);
				}
			},
			error: function (jqXHR, exception) {
				window.location.href = "/error"
			}
		});
	});

	function fillError(item) {
		//toDo
	}

});