app.factory("RatingService", function ($resource) {
    return $resource('/api/v1/reviews/addrating/:rating/bookurl/:bookurl',  {bookurl:'@bookurl', rating:'@rating'}, {update: {method: 'PUT'}});
});

app.factory("GetRatingService", function ($resource) {
    return $resource('/api/v1/reviews/getrating/bookurl/:bookurl',  {bookurl:'@bookurl'}, {update: {method: 'PUT'}});
});

app.factory("GetAllRatingService", function ($resource) {
    return $resource('/api/v1/reviews/getrating/:bookurl',  {bookurl:'@bookurl'}, {update: {method: 'PUT'}});
});

