app.controller("SearchController", [
    '$http',
    '$scope',
    'SearchInputService',
    function ($http, $scope, SearchInputService) {
        $scope.searchInput = "";

        debugger;

        /**
         Remove active class on submit
         **/
        $('form').submit(function (e) {
            e.preventDefault();
            SearchInputService.addInput("lalalala");
                window.location.href = "/api/v1/exploreresults";

        });

        /**
         Show/Hide form inputs
         **/
        $('.search span').click(function (e) {

            var $parent = $(this).parent();

            if (!$parent.hasClass('active')) {

                $parent
                    .addClass('active')
                    .find('input:first')
                    .on('blur', function () {
                            if (!$(this).val().length) $parent.removeClass('active');
                        }
                    );

            }
        });
}
]);
