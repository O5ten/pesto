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
        $location.url('/category/' + category);
    };
    var vote = function(vote, id){
        $http({
            method: 'PUT',
            url: '/api/paste/' + id + '/' + vote
        });
    };
    $scope.upvote = function(paste){
        if(!paste.hasVoted){
            paste.votes++;
            paste.hasVoted = true;
            vote('upvote', paste.id);
        }
    };
    $scope.downvote = function(paste){
        if(!paste.hasVoted){
            paste.votes--;
            paste.hasVoted = true;
            vote('downvote', paste.id);
        }
    };
    $http({
        method: 'GET',
        url: '/api/paste'
    }).then(function(response) {
        $scope.pastes = response.data.sort(function(a,b){
            if(a.votes < b.votes){
                return -1;
            }
            if(a.votes > b.votes){
                return 1;
            }
            return 0;
        }).reverse();
    }, function(response) {
        $scope.pastes = [];
    });
}).config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider
  .when('/home', {
      templateUrl: 'pages/collection.html',
      controller: 'Home'
  });
}]);
