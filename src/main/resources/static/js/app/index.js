var main = {
    init : function () {
        var _this = this;
        $('#search_btn').on('click', function () {
            _this.search();
        });
    },

    search : function () {
        var data = {
            keyWord: $('#key_word').val()
        };

        $.ajax({
            type: 'GET',
            url: '/api/search',
            data: data,
            dataType: "json",
            async: false,
            success: function(data) {
                $.each(data, function (idx, val) {
                    $('body').append(val.title).append("\n")
                        .append(val.prodYear).append("\n")
                        .append(val.nation).append("\n")
                        .append(val.posters).append("\n");
                });
            },
            error: function() {
                alert("값을 가져오지 못했습니다.");
            }
        });
    }
};

main.init();