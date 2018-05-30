var app = angular.module('app', ["ngResource", "ui.router"]);

app
    .config(
        function ($stateProvider, $urlRouterProvider) {

            $stateProvider
                .state('home', {
                    url: '/',
                    views: {
                        'auth': {
                            templateUrl: '/partials?name=auth',
                            controller: 'SignController'
                        },
                        'menu': {
                            templateUrl: '/partials?name=menu',
                            controller: 'HomeController'
                        },
                        'search': {
                            templateUrl: '/partials?name=searchbar',
                            controller: 'SearchController'
                        },
                        'signin': {
                            templateUrl: '/partials?name=sign-in',
                            controller: 'SignController'
                        },
                        'signup': {
                            templateUrl: '/partials?name=sign-up',
                            controller: 'SignController'
                        },
                        'exploregallery': {
                            templateUrl: '/partials?name=bookgallery',
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
    .factory("ExploreByTitleService", function ($resource) {
        return $resource('/api/v1/explore/bytitle/:title', {}, {update: {method: 'PUT'}});
    })
    .factory('Books', ['$resource',
        function ($resource) {
            return $resource('/api/v1/explore/bytitle/:title', {}, {
                query: {method: "GET", isArray: true},
                create: {method: "POST"},
                get: {method: "GET"},
                remove: {method: "DELETE"},
                update: {method: "PUT"}
            });

        }])
    .service("UserService", function () {
        var user = {};
        var userService = (function () {

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
    })
    .service("BooksService", function () {
        var books = {};
        var booksService = (function () {

            function setBooks(books) {
                this.books = books
            }

            function getBooks() {
                return this.books;
            }

            return {
                setBooks: setBooks,
                getBooks: getBooks
            };

        })();
        return booksService;
    })
    .factory('BooksService2', BooksService2);

BooksService2.$inject = ['$http'];

function BooksService2($http) {
    var endpointUrl = "/api/v1/explore/bytitle/hate";

    /* expose our API */
    var service = {
        getBookData: getBookData,
    }
    return service;

    function getBookData() {
        /* this is how you would call the server to get your data using $http */
        /* this will return a promise to the calling method (in the controller)
           when the server returns data this will 'resolve' and you will have access to the data
           in the controller.
           Notes on promises: http://andyshora.com/promises-angularjs-explained-as-cartoon.html
           */
        return $http.get(endpointUrl);
    }
}