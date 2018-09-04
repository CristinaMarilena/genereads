app.controller("AccountController", function($scope, AccountService) {

    $scope.accounts = AccountService.query(function(data) {
        console.log("Got all accounts", data);
    });

});