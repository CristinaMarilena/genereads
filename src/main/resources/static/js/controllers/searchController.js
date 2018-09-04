app.controller("SearchController", [
    '$http',
    '$scope',
    'SearchInputService',
    'DisplayFactory',
    function ($http,
              $scope,
              SearchInputService,
              DisplayFactory) {
        $scope.searchInput = "";

        debugger;

        /**
         Remove active class on submit
         **/
        $('form').submit(function (e) {
            e.preventDefault();
/*
            SearchInputService.input.push($scope.searchInput);
*/
            DisplayFactory.searched.push(true);
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
