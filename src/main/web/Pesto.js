angular.module('Pesto', [ 'ngRoute',
  'Pesto.Home',
  'Pesto.New',
  'Pesto.Paste',
  'Pesto.Category'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .otherwise({redirectTo: '/home'});
}]);
