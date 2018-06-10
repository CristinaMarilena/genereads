app.controller("BookController", [
    '$http',
    '$scope',
    'GetAccessedBookService',
    'ToReadService',
    'ReadingService',
    'RatingService',
    'GetRatingService',
    'GetAllRatingService',
    'ReviewService',
    'GetAllReviewsService',
    "AccountService",
    'CategoryByBookFactory',
    function ($http,
              $scope,
              GetAccessedBookService,
              ToReadService,
              ReadingService,
              RatingService,
              GetRatingService,
              GetAllRatingService,
              ReviewService,
              GetAllReviewsService,
              AccountService,
              CategoryByBookFactory) {

        $scope.accessedBook = undefined;

        $scope.class = "";
        $scope.readingClass = "";
        $scope.rating = undefined;
        $scope.allRating = undefined;
        $scope.review = "";
        $scope.reviews = [];

        GetAccessedBookService.get().$promise.then(function (result) {
            $scope.accessedBook = result;
            console.log("accessed book");
            console.log($scope.accessedBook);
            $http.get('/api/v1/books/author/byurl/' + $scope.accessedBook.apiUrl).then(function (response) {
                var authorsList = response.data;
                $scope.accessedBook.authors = authorsList[0];
            });


            CategoryByBookFactory.get({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result);
                $scope.accessedBook.category = result.categoryName;
            });

            ToReadService.get({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result.bookId);
                $scope.class = "btn-warning";
                if (result.bookId !== undefined) {
                    $scope.changeClass();
                }
            });

            ReadingService.get({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result.bookId);
                $scope.readingClass = "btn-inverse";
                if (result.bookId !== undefined) {
                    $scope.changeReadingClass();
                }
            });

            GetRatingService.get({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                if (result.rating !== undefined) {
                    if (result.rating === 2) {
                        $("#1stars").addClass('active').siblings().removeClass('active');
                    }
                    if (result.rating === 4) {
                        $("#2stars").addClass('active').siblings().removeClass('active');
                    }
                    if (result.rating === 6) {
                        $("#3stars").addClass('active').siblings().removeClass('active');
                    }
                    if (result.rating === 8) {
                        $("#4stars").addClass('active').siblings().removeClass('active');
                    }
                    if (result.rating === 10) {
                        $("#5stars").addClass('active').siblings().removeClass('active');
                    }
                }
            });

            GetAllRatingService.get({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result.rating);
                $scope.allRating = result.rating;
            });

            GetAllReviewsService.query({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result);
                $scope.reviews = result;
                angular.forEach($scope.reviews, function (rev) {
                    AccountService.get({id: rev.userId}).$promise.then(function (result) {
                        rev.username = result.username;
                    });
                });
            });

        });

        $scope.toRead = function () {
            ToReadService.save({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result);
            });
            $scope.changeClass();
        };

        $scope.reading = function () {
            ReadingService.save({bookurl: $scope.accessedBook.apiUrl}).$promise.then(function (result) {
                console.log(result);
            });
            $scope.changeReadingClass();
        };

        $scope.changeClass = function () {
            if ($scope.class === "btn-warning")
                $scope.class = "btn-success";
            else
                $scope.class = "btn-warning";
        };

        $scope.changeReadingClass = function () {
            if ($scope.readingClass === "btn-inverse")
                $scope.readingClass = "btn-success";
            else
                $scope.readingClass = "btn-inverse";
        };


        $('.stars li').on('click', function () {
            var el = $(this);
            el.addClass('active').siblings().removeClass('active');
            $('#rating').val(el.attr('title'));
            console.log(el.attr('title'));
            $scope.addRating(el.attr('title'));
        });

        $scope.addRating = function (rating) {
            RatingService.save({bookurl: $scope.accessedBook.apiUrl, rating: rating}).$promise.then(function (result) {
                console.log(result);
            });
        };

        $scope.addReview = function(){
            console.log("review");
            ReviewService.save({bookurl: $scope.accessedBook.apiUrl} ,$scope.review).$promise.then(function (result) {
                console.log(result);
                location.reload();
            });
            $scope.review = "";
        };
    }
]);
