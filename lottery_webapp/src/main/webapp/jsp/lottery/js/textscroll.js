$.fn.textScroll=function(){
    var speed=60,flag=null,tt,that=$(this),child=that.children();
    var p_w=that.width(), w=child.width();
    child.css({left:p_w});
    var t=(w+p_w)/speed * 1000;
    function play(m){
        var tm= m==undefined ? t : m;
        child.animate({left:-w},tm,"linear",function(){             
            $(this).css("left",p_w);
            play();
        });                 
    }
    child.on({
        mouseenter:function(){
            var l=$(this).position().left;
            $(this).stop();
            tt=(-(-w-l)/speed)*1000;
        },
        mouseleave:function(){
            play(tt);
            tt=undefined;
        }
    });
    play();
}