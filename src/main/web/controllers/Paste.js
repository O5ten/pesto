angular.module('Pesto.Paste', ['ngRoute'])
.controller('Paste', function($scope, $http, $routeParams) {
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
}).config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .when('/paste/:id', {
      templateUrl: 'pages/paste.html',
      controller: 'Paste'
  });
}]);
