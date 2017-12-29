(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('LocationBikeAll', LocationBikeAll);

    LocationBikeAll.$inject = ['$resource'];

    function LocationBikeAll($resource) {
        var service = $resource('api/location-electric-bikes/location/:position', {}, {
            'query': {method: 'POST', isArray: true}
        });
        return service;
    }
})();
