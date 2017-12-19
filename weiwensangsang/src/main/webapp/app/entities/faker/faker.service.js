(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('Faker', Faker);

    Faker.$inject = ['$resource'];

    function Faker ($resource) {
        var resourceUrl =  'api/fakers/:id';

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
