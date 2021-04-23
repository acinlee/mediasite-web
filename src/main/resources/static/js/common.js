$(function(){

// 페이징
	$('.paging .num_page').on("click",function(){
		$(this).addClass("active").siblings().removeClass("active");
	});

// 모달 팝업  닫기버튼 처리
    $('.modal_close').on('click',function(){
        $('.layer_position').removeClass("active");
	});

// 화면 클릭시 모달 팝업  닫기버튼 처리
    $('.layer_position .pop_back').on('click',function(){
        $('.layer_position').removeClass("active");
    });

});
