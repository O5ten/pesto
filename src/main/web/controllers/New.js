angular.module('Pesto.New', ['ngRoute'])
.controller('New', function($scope, $http, $location) {
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
        }).then(function(response){
            $location.url('/#/paste/' + response.data.id);
        });
    };
}).config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .when('/paste/new', {
      templateUrl: 'pages/paste.html',
      controller: 'New'
  });
}]);
