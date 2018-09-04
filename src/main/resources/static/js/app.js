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
                            controller: 'ExploreController'
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
    .factory("RecommendationService", function ($resource) {
        return $resource('/api/v1/rec', {}, {update: {method: 'PUT'}});
    })
    .factory("ExploreService", function ($resource) {
        return $resource('/api/v1/explore/by/:param', {}, {update: {method: 'PUT'}});
    })
    .factory("GetAccessedBookService", function ($resource) {
        return $resource('/api/v1/books/byaccessedurl', {});
    })
    .factory("CurrentlyReadingService", function ($resource) {
        return $resource('/api/v1/currentlyreading/own', {});
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

    app.factory('SearchInputService',
        ['$http',
            'ExploreService',
            'RecommendationService',
            'CurrentlyReadingService',
        function (
            $http,
            ExploreService,
            RecommendationService,
            CurrentlyReadingService) {

        var obj = {};

        obj.books = [];

        obj.searchInput = "";

        obj.serviceType = "";

        let service;

        obj.getBooks = function(){
            if(obj.serviceType === "search") {
                service = ExploreService.query({param: obj.searchInput + " ,"});
            }
            if(obj.serviceType === "currently"){
                service = CurrentlyReadingService.query({});
            }
            if(obj.serviceType === "rec"){
                service = RecommendationService.query({});
            }

            service.$promise.then(function (result) {
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

        obj.getServiceType = function (type) {
            debugger;
            if(type === "search")
                obj.serviceType = "search";
            if(type === "rec")
                obj.serviceType = "rec";
            if(type === "read")
                obj.serviceType = "read";
            if(type === "toread")
                obj.serviceType = "toread";
            if(type === "currently")
                obj.serviceType = "currently";
            if(type === "recently")
                obj.serviceType = "recently";
        };

        return obj;
    }]);

app.factory('DisplayFactory', [function () {

    var obj = {};

    obj.searched = false;

    return obj;
}]);
