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
                            controller: 'ExploreController'
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
                            controller: 'ExploreController'
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
    });

    app.factory('SearchInputService', ['$http', 'ExploreByTitleService',function ($http, ExploreByTitleService) {

        var obj = {};

        obj.books = [];

        obj.searchInput = "";


        obj.getBooks = function(){
            ExploreByTitleService.query({title: obj.searchInput+ " ,"}).$promise.then(function (result) {
                obj.books = result;
                console.log(obj.books);
                angular.forEach(obj.books, function (book1) {
                    if (book1.title.length > 30) {
                        book1.title = book1.title.substring(0, 30);
                    }
                });

                angular.forEach(obj.books, function (book2) {
                    $http.get('/api/v1/books/author/byurl/' + book2.apiUrl).then(function (response) {
                        var authorsList = response.data;
                        if (authorsList !== "undefined") {
                            book2.authors = authorsList[0].substring(0, 20);
                        } else {
                            book2.authors = "";
                        }
                        console.log(book2.authors);
                    });
                });

                console.log(obj.books);
            });
        };


        /*        SearchInputService.addInput("something");*/
        console.log(obj.searchInput);
        return obj;
    }]);
