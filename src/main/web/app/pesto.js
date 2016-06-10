var pestoApp = angular.module('pesto', []);

pestoApp.controller('pestoController', function pestoController($scope) {
  $scope.pastes = [
    {
      title: 'HelloWorld.kt',
      language: 'Kotlin',
      code: 'package hello\n' +
'\n' +
'fun main(args: Array<String>) {\n' +
'   println("Hello World!")\n' +
'}'
    }, {
      title: 'HelloWorld.c',
      language: 'C',
      code: '#include <stdio.h>\n' +
'\n' +
'int main(void)\n' +
'{\n' +
'    printf("Hello, world!\\n");\n' +
'    return 0;\n' +
'}'
    }, {
        title: 'HelloWorld.erl',
        language: 'Erlang',
        code: '-module(hello).\n'+
'\n' +
'-export([hello/0]).\n' +
'\n' +
'hello() -> io:format("Hello, world!~n").'
    }
  ];
});
