app.factory("ToReadService", function ($resource) {
    return $resource('/api/v1/toread/byurl/:bookurl',  {bookurl:'@bookurl'}, {update: {method: 'PUT'}});
})