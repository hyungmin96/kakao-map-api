// * 게시글 작성자 api

const data = {
    residence: '',
    longitude: '',
    latitude: '',
    location: '',
    preferTime: ''
}

$(document).on('click', '.saveExchangeInfoButton', function (){
    saveExchangeInfo();
})

function saveExchangeInfo(){
    data.residence = $('#sample5_address').val() + ' ' + $('.inputbox')[0].value
    data.longitude = $('.longitudeValue')[0].value
    data.latitude = $('.latitudeValue')[0].value
    data.location = $('#exchange_address').val()
    data.preferTime = $('#exchange_time').val()

    $.ajax({
        url: '/api/exchange/save/writer/location',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response){
            console.log(response)
        }
    })
}


