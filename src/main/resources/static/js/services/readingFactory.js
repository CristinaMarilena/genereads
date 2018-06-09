app.factory("ReadingService", function ($resource) {
    return $resource('/api/v1/reading/byurl/:bookurl',  {bookurl:'@bookurl'}, {update: {method: 'PUT'}});
})