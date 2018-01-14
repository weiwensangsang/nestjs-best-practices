(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('FaceCheck', FaceCheck);

    FaceCheck.$inject = ['$resource'];

    function FaceCheck($resource) {
        var service = $resource('api/fakers/face/face-operation/:control', {}, {
            'save' : {method : 'POST'}
        });
        return service;
    }
})();
