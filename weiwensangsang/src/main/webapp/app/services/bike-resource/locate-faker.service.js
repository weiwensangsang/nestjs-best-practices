(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('FakerLocate', FakerLocate);

    FakerLocate.$inject = ['$resource'];

    function FakerLocate($resource) {
        var service = $resource('api/fakers/locate', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
