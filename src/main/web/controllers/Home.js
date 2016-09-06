angular.module('Pesto.Home', ['ngRoute'])
.controller('Home', function pestoController($scope, $http, $route, $routeParams, $location) {
    $scope.pastes = [];
    $scope.$watch('pastes', function(){
        $scope.$evalAsync(function() {
            microlight.reset();
        });
    });
    $scope.onRemove = function(id){
        $http({
            method: 'DELETE',
            url: '/api/paste/' + id
        }).then(function(response) {
            $location.url('/#/Home');
        });
    };
    $scope.goHome = function(){
        $location.url('/Home');
    };
    $scope.editPaste = function(id){
        $location.url('/paste/' + id);
    };
    $scope.createNew = function(){
        $location.url('/paste/new');
    };
    $scope.onCopy = function(code){
        console.log('Copied ' + code + 'to clipboard');
    };
    $scope.onCategory = function(category) {
        $location.url('/Home/' + category);
    }
    $http({
        method: 'GET',
        url: '/api/paste'
    }).then(function(response) {
        $scope.pastes = response.data;
    }, function(response) {
        $scope.pastes = [];
    });
}).config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .when('/home', {
      templateUrl: 'pages/home.html',
      controller: 'Home'
  });
}]);
