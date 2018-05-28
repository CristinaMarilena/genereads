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
    })
    .service("UserService", function () {
        var user = {};
        var userService = (function() {

            function setUser(user) {
                this.user = user
            }

            function getUSer() {
                return this.user;
            }

            return {
                setUser: setUser,
                getUser: getUSer
            };

        })();
        return userService;
    });