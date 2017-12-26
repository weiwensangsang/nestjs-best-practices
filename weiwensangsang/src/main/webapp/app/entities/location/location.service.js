(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('Location', Location);

    Location.$inject = ['$resource'];

    function Location ($resource) {
        var resourceUrl =  'api/locations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET' },
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
