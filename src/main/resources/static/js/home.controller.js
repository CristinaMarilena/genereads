
app.controller("homeController", function($http, $location, AccountService, AccountEmailService){

    $scope.loginButton = true;
    $scope.signUpButton = true;
    $scope.userAccount = new AccountService();



});
