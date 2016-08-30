angular.module('pesto', ['ngRoute'])

.controller('MainController', function pestoController($scope, $http, $route, $routeParams, $location) {
    $scope.$watch('pastes', function(){
        $scope.$evalAsync(function() {
            microlight.reset();
        });
    });
    $http({
        method: 'GET',
        url: '/api/paste'
    }).then(function(response) {
        $scope.pastes = response.data;
    }, function(response) {
        $scope.pastes = [];
    });
})

.controller('AllController', function($scope, $http) {
    $scope.$watch('pastes', function(){
       $scope.$evalAsync(function() {
           microlight.reset();
       });
    });
    $http({
      method: 'GET',
      url: '/api/paste'
    }).then(function(response) {
        $scope.pastes = response.data;
    }, function(response) {
          $scope.pastes = [];
    });
})

.controller('PasteController', function($scope, $http, $routeParams) {
    $scope.$watch('pastes', function(){
        $scope.$evalAsync(function() {
            microlight.reset();
        });
    });

    $http({
        method: 'GET',
        url: '/api/paste/' + $routeParams.id
    }).then(function(response) {
        $scope.pastes = [response.data];
        $scope.$watch('pastes', function(){
            $scope.$evalAsync(function() {
                microlight.reset();
            });
        });
        $scope.resetMicrolight = function(){
            $scope.$evalAsync(function() {
                microlight.reset();
            });
        };
    }, function(response) {
        $scope.singlePaste = {
            id: '404',
            title: '404',
            code: '404 Code snippet not found!'
        };
    });
})

.config(function($routeProvider) {
    $routeProvider
    .when('/paste/:id', {
        templateUrl: 'paste.html',
        controller: 'PasteController'
    })
    .when('/paste', {
        templateUrl: 'pastes.html',
        controller: 'AllController'
    }).otherwise({
        redirectTo: '/paste'
    });
});
