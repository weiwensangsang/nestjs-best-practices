(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('GenerateBike', GenerateBike);

    GenerateBike.$inject = ['$resource'];

    function GenerateBike($resource) {
        var service = $resource('api/electric-bikes/control', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
