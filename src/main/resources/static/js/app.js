var app = angular.module('app', ["ngResource", "ui.router"]);

app
    .config([ '$stateProvider'
        , '$urlRouterProvider'
        , function($stateProvider, $urlRouterProvider) {

            $stateProvider
                .state('home', {
                    url: '/',
                    views:{
                        'auth':{
                            templateUrl: '/partials?name=auth',
                            controller: 'signController'
                        },
                        'menu':{
                            templateUrl: '/partials?name=menu',
                            controller: 'signController'
                        }
                    }
                });
            $urlRouterProvider.otherwise('/');

        }
    ]);



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

            function getUser() {
                return this.user;
            }

            return {
                setUser: setUser,
                getUser: getUser
            };

        })();
        return userService;
    });