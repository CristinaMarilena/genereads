app.controller("SearchController", [
    '$http',
    '$scope',
    'ExploreByTitleService',
    function ($http, $scope, ExploreByTitleService) {
        $scope.booklist = [];
        $scope.searchInput = "";

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

        $scope.books = [];
        debugger;

        ExploreByTitleService.query({title: 'Mother'}).$promise.then(function (result) {
            $scope.books = result;
            console.log($scope.books);
            angular.forEach($scope.books, function (book) {
                if (book.title.length > 30) {
                    book.title = book.title.substring(0, 30);
                }
            });

            angular.forEach($scope.books, function (book) {
                $http.get('/api/v1/books/author/byurl/' + book.apiUrl).then(function(response) {
                    book.authors = response.data;
                    console.log(response.data);
                });
            })

            console.log($scope.books);
        });

}
]);
