app.factory("CategoryByBookFactory", function ($resource) {
    return $resource('/api/v1/books/category/byurl/:bookurl',  {bookurl:'@bookurl'}, {update: {method: 'PUT'}});
});