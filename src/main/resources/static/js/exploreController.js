app.controller("ExploreController", [
    '$http',
    '$scope',
    'ExploreByTitleService',
    'SearchInputService',
    function ($http, $scope, ExploreByTitleService, SearchInputService, DisplayFactory) {

        $scope.SearchInputService = SearchInputService;
        $scope.DisplayFactory = DisplayFactory;
    }
]);
