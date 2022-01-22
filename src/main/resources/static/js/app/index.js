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
            data: data
        }).done(function () {
            alert('검색완료');
        }).fail(function (error) {
           alert(JSON.stringify(error));
        });
    }
};

main.init();