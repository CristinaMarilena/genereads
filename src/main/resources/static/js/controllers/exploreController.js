app.controller("ExploreController", [
    '$http',
    '$scope',
    'ExploreService',
    'SearchInputService',
    function ($http, $scope, ExploreService, SearchInputService, DisplayFactory) {

        $scope.SearchInputService = SearchInputService;
        $scope.DisplayFactory = DisplayFactory;
    }
]);
