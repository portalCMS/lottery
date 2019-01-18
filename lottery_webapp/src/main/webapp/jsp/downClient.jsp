<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户端下载</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
 <style>
        /*常量CSS定位*/

        html, body, div, p, ul, li, h1, h2, h3, h4, h5, h6, form, input, select, button, textarea,table, th, td { margin: 0; padding: 0; }
        img { border: 0 none; vertical-align: top; }
        h1, h2, h3, h4, h5, h6 { font-size: 14px; }
        body, input, select, button, textarea { font-size: 12px; font-family: Microsoft YaHei, Geneva, sans-serif; }
        ul, li { list-style-type: none; }
        .fl{  float: left; }
        .fr { float: right; }

        a, a:link { color: #222; text-decoration: none; }
        a:visited {  }
        a:active, a:hover { text-decoration: underline; }
        a:focus { outline: none; }

        .d_xplain{height: 177px; background: #fff; }
        .d_xplain ul li{float: left;  padding: 20px 110px 0px 125px;}
        .d_x_box{width: 1006px; margin: 0 auto;}
        .d_x_box h2{text-align: center; padding-top: 10px;}

    </style>
    
</head>
<body>
	<%@ include file="include/head.jsp"%>
	<!-- content -->
    <div style="width: 100%; background: #99ccff url(${contextPath }/jsp/img/cloud_bg.png) 0 0 no-repeat;">
        <div style="position: relative; width:1200px; margin: 0 auto;  height: 515px; ">
            <div style="width: 1200px; position: absolute;">
                <img src="${contextPath }/jsp/img/PC_bg.png" style="position: absolute; margin-top: 152px;  margin-left: 45px;">
            </div>
            <div style="width: 659px;float: right;padding-right: 0px;margin-top: 120px;">
                <a href="#" style="font-size: 25px;">使用最新谷歌内核打造, 让您购彩安全, 体验舒心。</a><br>
                <a href="#" style="font-size: 16px;">本平台为了大家购彩安全与快捷还望大家依流程进行使用，确保资金无误！<br>
                    夺金客户端还在优化中，请大家持续关注平台公告！</a></br>
                <a href="http://www.dj365.info:909/dj.zip" style="margin-left: 230px;">
                    <img src="${contextPath }/jsp/img/down_btn.png" style="margin-top: 20px; ">
                </a></br>
                <a href="http://www.dj365.info:909/CaiP.zip" style="margin-left: 230px;">
                    <img src="${contextPath }/jsp/img/down_btn2.png" style="margin-top: 20px; ">
                </a>
            </div>
        </div>
        <div class="d_xplain" >
           <div class="d_x_box">
               <ul>
                   <li>
                       <a href="#"><img src="${contextPath }/jsp/img/safety.png"></a>
                       <h2>安全</h2>
                   </li>
                   <li>
                       <a href="#"><img src="${contextPath }/jsp/img/quick.png"></a>
                       <h2>快捷</h2>
                   </li>
                   <li>
                       <a href="#"><img src="${contextPath }/jsp/img/stable.png"></a>
                       <h2>稳定</h2>
                   </li>
                   <div style="clear: both;"></div>
               </ul>
           </div>

        </div>
         <div style="width: 100%; background-color:#dcdcdc; text-align: center; ">
            <img src="${contextPath }/jsp/img/down_test_0.jpg">
        </div>
        <div style="width: 100%; background-color:#fff; text-align: center; ">
            <img src="${contextPath }/jsp/img/down_test_1.jpg">
        </div>
        <div style="width: 100%; background-color:#fff; text-align: center; ">
            <img src="${contextPath }/jsp/img/down_test_2.jpg">
        </div>
        <div style="width: 100%; background-color:#fff; text-align: center; ">
            <img src="${contextPath }/jsp/img/down_test_3.jpg">
        </div>
        <div style="width: 100%; background-color:#fff; text-align: center; ">
            <img src="${contextPath }/jsp/img/down_test_4.jpg">
        </div>
    </div>
	<!-- content End -->
	<%@ include file="include/footer.jsp"%>

</body>
</html>