(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('ElectricBike', ElectricBike);

    ElectricBike.$inject = ['$resource'];

    function ElectricBike ($resource) {
        var resourceUrl =  'api/electric-bikes/:id';

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
