app.controller("signController", function ($http, $location, $scope, AccountService) {


    function authentication() {

        var self = this;
        $http.get("/user").success(function (data) {
            self.user = data.userAuthentication.details.name;
            self.authenticated = true;
        }).error(function () {
            self.user = "N/A";
            self.authenticated = false;
        });

        /*
         The logout function does a POST to "/logout" and then clears the authenticated flag.
         */
        self.logout = function () {
            $http.post("/logout", {}).success(function () {
                self.authenticated = false;
                $location.path("/");
            }).error(function (data) {
                console.log("Logout failed");
                self.authenticated = false;
            });
        };

    }

    $scope.confirmPassword = "";
    $scope.notMatchedPasswords = false;

    document.getElementById("sign-up-form").onsubmit = function () {
        signUp()
    };

    $scope.account = new AccountService();

    function signUp() {

        if ($scope.confirmPassword == $scope.account.password) {

            $scope.addAccount = function () { //create a new movie. Issues a POST to /api/movies
                $scope.account.$save(function () {
                    console.log("Account created succesfully"); // on success go back to home i.e. movies state.
                });
            };

            $scope.addAccount();
            $scope.notMatchedPasswords = false;
            window.location.href = "/";
        }
        else{
            $scope.notMatchedPasswords = true;
        }
    }
});


