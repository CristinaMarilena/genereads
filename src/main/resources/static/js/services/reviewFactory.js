app.factory("ReviewService", function ($resource) {
    return $resource('/api/v1/reviews/addreview/bookurl/:bookurl', {
        bookurl: '@bookurl'
    }, {update: {method: 'PUT'}});
});
app.factory("GetAllReviewsService", function ($resource) {
    return $resource('/api/v1/reviews/allreviews/:bookurl', {bookurl: '@bookurl'}, {update: {method: 'PUT'}});
});