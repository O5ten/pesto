angular.module('Pesto.Category', ['ngRoute'])
.controller('Category', function pestoController($scope, $http, $route, $routeParams, $location) {
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
        $location.url('/home');
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
    $http({
        method: 'GET',
        url: '/api/paste/by/language/as/' + $routeParams.category
    }).then(function(response) {
        $scope.pastes = response.data;
    }, function(response) {
        $scope.pastes = [];
    });
}).config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .when('/home/:category', {
      templateUrl: 'pages/home.html',
      controller: 'Category'
  });
}]);
