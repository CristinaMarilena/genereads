var app = angular.module('app', ["ngResource", "ui.router"]);

app
    .config(
        function($stateProvider, $urlRouterProvider) {

            $stateProvider
                .state('home', {
                    url: '/',
                    views:{
                        'auth':{
                            templateUrl: '/partials?name=auth',
                            controller: 'SignController'
                        },
                        'menu':{
                            templateUrl: '/partials?name=menu',
                            controller: 'HomeController'
                        },
                        'search':{
                            templateUrl: '/partials?name=searchbar',
                            controller: 'SearchController'
                        },
                        'signin':{
                            templateUrl: '/partials?name=sign-in',
                            controller: 'SignController'
                        },
                        'signup':{
                            templateUrl: '/partials?name=sign-up',
                            controller: 'SignController'
                        }
                    }
                });
            $urlRouterProvider.otherwise('/');

        }
    );



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