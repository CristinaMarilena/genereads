app.controller("ExploreController", [
    '$http',
    '$scope',
    'ExploreByTitleService',
    'SearchInputService',
    function ($http, $scope, ExploreByTitleService, SearchInputService) {
        $scope.booklist = [];

        $scope.books = [];
        debugger;

        function getBooks() {
            ExploreByTitleService.query({title: 'love'}).$promise.then(function (result) {
                $scope.books = result;
                console.log($scope.books);
                angular.forEach($scope.books, function (book) {
                    if (book.title.length > 30) {
                        book.title = book.title.substring(0, 30);
                    }
                });

                angular.forEach($scope.books, function (book) {
                    $http.get('/api/v1/books/author/byurl/' + book.apiUrl).then(function (response) {
                        var authorsList = response.data;
                        if (authorsList !== "undefined") {
                            book.authors = authorsList[0].substring(0, 20);
                        } else {
                            book.authors = "";
                        }
                        console.log(book.authors);
                    });
                })

                console.log($scope.books);
            });
        }

        getBooks();


/*        SearchInputService.addInput("something");*/
        console.log(SearchInputService.getInput());


    }
]);
