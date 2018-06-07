app.controller("ExploreController", [
    '$http',
    '$scope',
    'ExploreByTitleService',
    'SearchInputService',
    function ($http, $scope, ExploreByTitleService, SearchInputService) {

        $scope.SearchInputService = SearchInputService;
    }
]);
