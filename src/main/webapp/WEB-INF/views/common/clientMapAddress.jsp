<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>로컬바터 - 지역 물품교환 및 거래</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
<div id="clickLatlng"></div>
<div id="userNumber"></div>
<div id="userAddr"></div>
<div id="clientAddr"></div>
<div class="serachAddr">
    <input type="text" name="title" id="serachAddrText" class="inputbox" placeholder="그룹 이름을 설정해주세요."/>
    <button class="searchButton"></button>
</div>
<div id="map" style="width:800px;height:600px;"></div>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/clientMapAddress.js"></script>
<script type="text/javascript" src="/js/lineTheMap.js"></script>
</body>
</html>