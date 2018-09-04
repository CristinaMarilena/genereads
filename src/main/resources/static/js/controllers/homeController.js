
app.controller("HomeController", function($scope, $http, $location, AccountService, AccountEmailService){

    $scope.loginButton = true;
    $scope.signUpButton = true;
    $scope.userAccount = new AccountService();
});
