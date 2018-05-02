var app = angular.module('app', ["ngResource"]);

app.factory("AccountService", function($resource) {
    return $resource('/api/v1/accounts/:id', {}, {update: { method: 'PUT' }});
});