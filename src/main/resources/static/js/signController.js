app.controller("SignController", function ($http, $location, $scope, AccountService, AccountEmailService,
                                           LoginService, UserService) {


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
    $scope.invalid = false;

    document.getElementById("sign-up-form").onsubmit = function () {
        signUp()
    };

    $scope.account = new AccountService();

    function signUp() {

        if ($scope.confirmPassword === $scope.account.password) {

            $scope.addAccount = function () { //create a new account. Issues a POST to /api/v1/accounts
                $scope.account.$save(function () {
                    console.log("Account created succesfully"); // on success go back to home i.e. account state.
                });
            };

            $scope.addAccount();
            $scope.invalid = false;
            window.location.href = "/";
        }
        else {
            $scope.invalid = true;
        }
    }


    /*Login*/
    document.getElementById("login-form").onsubmit = function () {
        login()
    };

    $scope.loggedAccount = new LoginService();

    function login() {
        debugger;
        var account = AccountEmailService.get({email: $scope.account.email}, function () {
            console.log(account.email);


            $scope.loggedAccount.email = account.email;
            $scope.loggedAccount.password = account.password;
            $scope.loggedAccount.username = account.username;

            $scope.loginAccount = function () {
                $scope.loggedAccount.$save(function () {
                    console.log("Account logged in successfully");
                    UserService.setUser(account);
                    window.location.href = "/";
                    // on success go back to home i.e. account state.
                });
            };

            $scope.loginAccount();
        });


    }


});


