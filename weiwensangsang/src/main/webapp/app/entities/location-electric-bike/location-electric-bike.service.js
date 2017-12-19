(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('LocationElectricBike', LocationElectricBike);

    LocationElectricBike.$inject = ['$resource'];

    function LocationElectricBike ($resource) {
        var resourceUrl =  'api/location-electric-bikes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
