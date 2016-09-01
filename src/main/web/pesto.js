angular.module('pesto', ['ngRoute'])
.controller('MainController', function pestoController($scope, $http, $route, $routeParams, $location) {
    $scope.pastes = [];
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
    $scope.pastes = [];
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
.controller('NewController', function($scope, $http) {
    $scope.pastes = [{id: '', title: 'new Paste', author: 'Anonymous', language: '', code: ''}];
    $scope.$watch('pastes', function(){
       $scope.$evalAsync(function() {
           microlight.reset();
       });
    });
    $scope.resetMicrolight = function(){
        $('.paste-content').text($scope.pastes[0].code);
        $scope.$evalAsync(function() {
            microlight.reset();
        });
    };
    $scope.onSubmit = function(){
        var paste = $scope.pastes[0];
        $http({
            method: 'POST',
            url: '/api/paste',
            data: {
                id: paste.id,
                title: paste.title,
                author: paste.author,
                language: paste.language,
                code: paste.code
            }
        });
    };
})
.controller('PasteController', function($scope, $http, $routeParams) {
    $scope.pastes = [];
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
            $('.paste-content').text($scope.pastes[0].code);
            $scope.$evalAsync(function() {
                microlight.reset();
            });
        };
        $scope.onSubmit = function(){
            var paste = $scope.pastes[0];
            $http({
                method: 'PUT',
                url: '/api/paste/' + $routeParams.id,
                data: {
                    title: paste.title,
                    language: paste.language,
                    code: paste.code,
                    id: paste.id
                }
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
    .when('/paste/new', {
        templateUrl: 'paste.html',
        controller: 'NewController'
    })
    .when('/paste/:id', {
        templateUrl: 'paste.html',
        controller: 'PasteController'
    }).when('/paste', {
        templateUrl: 'pastes.html',
        controller: 'AllController'
    }).otherwise({
        redirectTo: '/paste'
    });
});
