app.controller("signInController", function($http, $location){
    var self = this;
    $http.get("/user").success(function(data) {
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
        $http.post("/logout", {}).success(function(){
            self.authenticated = false;
            $location.path("/");
        }).error(function (data) {
            console.log("Logout failed");
            self.authenticated = false;
        });
    };
});
