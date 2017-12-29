(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('LocationDetail', LocationDetail);

    LocationDetail.$inject = ['$resource'];

    function LocationDetail($resource) {
        var service = $resource('api/electric-bikes/get/{position}', {}, {
            'query': {method: 'GET'}
        });
        return service;
    }
})();
