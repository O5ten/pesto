angular.module('Pesto', [ 'ngRoute',
  'Pesto.Home',
  'Pesto.New',
  'Pesto.Paste'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .otherwise({redirectTo: '/Home'});
}]);
