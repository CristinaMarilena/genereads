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
           //console.log(data);
           $scope.booklist = data;
            //console.log($scope.booklist);
            BooksService.setBooks($scope.booklist);

            console.log("service books")
            //console.log(BooksService.getBooks());
        });
        //console.log($scope.booklist);
    };

    $scope.getBook();





    function onSuccess(res) {
        console.log(res.data);
        $scope[booklist] = res.data.map(function(book) {return book});
        $scope.$apply();
    }

    function onFailure(err) {
        console.log("Failed")
    }


    async function pulaMea() {
        return await $http.get('/api/v1/explore/bytitle/hate');
    }

    pulaMea().then((res) => {
        $scope.booklist = Object.assign({},res.data)
        console.log($scope.booklist)        // da ce trebuie
    });

    console.log($scope.booklist)        // du-te-n pula mea george, muie george, muieeeeeee


});
