(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('FakerActivate', FakerActivate);

    FakerActivate.$inject = ['$resource'];

    function FakerActivate($resource) {
        var service = $resource('api/fakers/activate', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
