app.controller("SearchController", function ($http, $scope, Books, BooksService2) {
    $scope.booklist = [];

    /**
     Remove active class on submit
     **/
    $('form').submit(function (e) {
        e.preventDefault();
        window.location.href = "/api/v1/exploreresults";

    });

    /**
     Show/Hide form inputs
     **/
    $('.search span').click(function (e) {

        var $parent = $(this).parent();

        if (!$parent.hasClass('active')) {

            $parent
                .addClass('active')
                .find('input:first')
                .on('blur', function () {
                        if (!$(this).val().length) $parent.removeClass('active');
                    }
                );

        }
    });

    debugger;

    $scope.booklist = [];

    // Get a single product
    $scope.getBook = function() {
        Books.query({title: 'hate'}, function(data) {
           console.log(data);
           $scope.booklist = data;
            console.log($scope.booklist);
            BooksService.setBooks($scope.booklist);

            console.log("service books")
            console.log(BooksService.getBooks());
        });
        console.log($scope.booklist);
    };

    $scope.getBook();


});
