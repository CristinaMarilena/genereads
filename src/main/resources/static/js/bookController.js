app.controller("BookController", [
    '$http',
    '$scope',
    'GetAccessedBookService',
    function ($http, $scope, GetAccessedBookService) {

        $scope.accessedBook = undefined;

        GetAccessedBookService.get().$promise.then(function (result) {
            $scope.accessedBook = result;
            console.log("accessed book");
            console.log($scope.accessedBook);
            $http.get('/api/v1/books/author/byurl/' + $scope.accessedBook.apiUrl).then(function (response) {
                var authorsList = response.data;
                console.log(authorsList.length);
                $scope.accessedBook.authors = authorsList[0];
            });
        });
    }
]);
