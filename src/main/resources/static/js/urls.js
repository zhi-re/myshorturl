function getShortUrl() {
    var str = $("#url").val();
    console.log(str);
    $.ajax({
        cache: true,
        type: "POST",
        url: "/url/save",
        data: {"oldUrl": str},
        async: false,
        error: function (data) {
            alert(data.responseJSON.message);
        },
        success: function (data) {
            $('#sUrlDiv').removeClass("hidden")
            if (data.code == 0) {
                $('#sUrl').html(data.data);
            }
        }
    });

}

function getHost() {
    var protocol = window.location.protocol;
    var host = window.location.host;
    return protocol + '//' + host + '/';
}