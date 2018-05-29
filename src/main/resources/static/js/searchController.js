(function(app){
app.controller("SearchController", function () {

    /**
     Remove active class on submit
     **/
    $('form').submit(function(e) {
        e.preventDefault();
    });

    /**
     Show/Hide form inputs
     **/
    $('.search span').click(function(e) {

        var $parent = $(this).parent();

        if (!$parent.hasClass('active')) {

            $parent
                .addClass('active')
                .find('input:first')
                .on('blur', function() {
                        if (!$(this).val().length) $parent.removeClass('active');
                    }
                );

        }
    });

})
})(app);