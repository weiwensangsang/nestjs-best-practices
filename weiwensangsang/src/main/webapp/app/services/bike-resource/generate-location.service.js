(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('GenerateLocation', GenerateLocation);

    GenerateLocation.$inject = ['$resource'];

    function GenerateLocation($resource) {
        var service = $resource('api/locations/generate', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
