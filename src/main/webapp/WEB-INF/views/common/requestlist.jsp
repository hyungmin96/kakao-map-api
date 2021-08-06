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

    <div class="exchangeRequestContainer">

    </div>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    $(document).ready(function(){

        const data = {
            page: 0,
            display: 10,
            groupId: 1,
            boardId: 5
        }

        $.ajax({
            url: '/api/exchange/get_request_list',
            type: 'GET',
            data: data,
            success: function(response){
                console.log(response)
                $.each(response.content, function(key, value){
                    $('.exchangeRequestContainer').append(
                        "<div style='margin: 3px 0 3px 0; border: 1px solid black;'>" +
                            value.content + "<br />" +
                            value.price + "<br />" +
                            value.request +
                        "<button class='exchangeButton'>교환</button>" +
                        "</div>"
                    )
                })
            }
        })

    })

</script>