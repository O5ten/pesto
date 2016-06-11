var pestoApp = angular.module('pesto', []);

pestoApp.controller('pestoController', function pestoController($scope, $http) {
  $scope.$watch('pastes', function(){
     $scope.$evalAsync(function() {
         microlight.reset();
     });
  });
  $http({
    method: 'GET',
    url: '/paste'
  }).then(function(response) {
      $scope.pastes = response.data;
    }, function(response) {
      $scope.pastes = [];
    });
});
