// Ĭ�����п������ɹ�����ø÷���
function changeCard(){
		$('.bankcard .default').removeClass('default');
		$(cardid).addClass('default');
}



// �ύ��Ϣ�����ش���������ø÷�����
// status�ֱ��Ӧ����״̬��submitting���ύ�� �� success: �����ɹ� �� error: ����ʧ��
// msgΪ��ʾ��Ϣ���ÿ�����ʾ����״̬��Ĭ����Ϣ��
function showProgress(status,msg) {
	var progresslayer = $(".popup:not('.offstage') .progress");
	var progresstext = '';

	if(msg != '') {
		progresstext = msg;
	} else {
		switch(status)
		{
			case 'submitting':
			  	progresstext = "������...";
			  break;
			case 'success':
				progresstext = "�����ɹ�";
			  break;
			case 'error':
				progresstext = "�ύ�������Ժ�����";
			  break;
			default:
			  progresstext = "�ύ�������Ժ�����";
			  	
		}
	}

	progresslayer.addClass('shown').children('.'+status).removeClass('hide').attr('id',status).siblings('div').attr('id','').addClass('hide');
	progresslayer.children('.progresstext').html(progresstext);
	if(status != 'submitting') {
		setTimeout(function() {
		  	 closePopup();
		}, 2200);
	}	
}


// ��Ҫ�رյ������ڣ��ɵ��ø÷���������ʾ����״̬�²��ص���
function closePopup() {
		var currentpopup = $(".popup:not('.offstage')");
		var progresslayer = $(currentpopup).children('.progress');
		currentpopup.addClass('offstage');
		progresslayer.removeClass('shown').children('div').addClass('hide').attr('id','');
		progresslayer.children('.progresstext').html('');
		$('.overlay').addClass('hide');
}




$(document).ready(function() {
	$('.tabs').on('click', "li:not('.active')", function() {
		var target = $(this).attr("class");
		$('.tabs .active').removeClass('active');
		$(this).addClass('active');
		$('.tabcontents .active').removeClass('active');
		$('.tabcontents #'+target).addClass('active');
	});



$('.callpopup').on('click', '', function(event) {
	var target = $(this).data('target'),
	 	popup = $(target),
		overlay = $('.overlay');
	popup.removeClass('offstage').find('.close').one('click', closePopup);
	overlay.removeClass('hide');
});

	
});
