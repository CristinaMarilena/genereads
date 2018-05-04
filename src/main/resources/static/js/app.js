var app = angular.module('app', ["ngResource"]);

app
    .factory("AccountService", function ($resource) {
        return $resource('/api/v1/accounts/:id', {}, {update: {method: 'PUT'}});
    })
    .factory("AccountEmailService", function ($resource) {
        return $resource('/api/v1/account/:email', {}, {update: {method: 'PUT'}});
    })
    .factory("LoginService", function ($resource) {
        return $resource('/login/v1', {}, {update: {method: 'PUT'}});
    });