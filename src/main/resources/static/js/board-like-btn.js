$(document).ready(()=>{
    $(".btn-like").on('click', function(e) {
        var onoff;
        var dataYn = 'N';

        var btn2 = $(e.target).siblings(".btn");
        if($(e.target).data('like') === 'Y'){
            dataYn = likeBtnColorFunction("danger", "primary", $(e.target), btn2);
        }
        else{
            dataYn = likeBtnColorFunction("primary", "danger", $(e.target), btn2);
        }

        var url ='/user/like/' + $('#board-id').val();
        onoff = $(e.target).data('onoff');
        $.ajax({
                url: url,
                data: {YN: dataYn, onOff: onoff},
                type: 'post',
                dataType: 'json',
                success: (data) => {

                    var targetNum =  $(e.target).data("num");
                    var siblingNum = btn2.data('num');

                    $(e.target).text( $(e.target).data('like') +  ' ' + targetNum);
                    btn2.text(btn2.data('like') +  ' ' + siblingNum);
                },
                error: () => {

                }
            }
        );
    });



    function likeBtnColorFunction(color1, color2, btn1, btn2){
        var onoff = $(btn1).data('onoff');
        let rtnYN = 'N';
        if(onoff===0){
            btn1.addClass("btn-" + color1);
            btn1.removeClass("text-" + color1);
            btn1.addClass("text-white");
            btn1.data('onoff', 1);
            btn1.data('num', btn1.data('num') + 1);

            rtnYN = btn1.data('like');

            var btn2onOff = btn2.data('onoff');
            if(btn2onOff === 1){
                btn2.removeClass("btn-" + color2);
                btn2.addClass("text-" +color2);
                btn2.removeClass("text-white");
                btn2.data('onoff', 0);
                btn2.data('num', btn2.data('num') - 1);
            }
        }
        else{
            btn1.removeClass("btn-" + color1);
            btn1.addClass("text-" + color1);
            btn1.removeClass("text-white");
            btn1.data('onoff', 0);
            btn1.data('num', btn1.data('num') - 1);
        }

        return rtnYN;
    }
});