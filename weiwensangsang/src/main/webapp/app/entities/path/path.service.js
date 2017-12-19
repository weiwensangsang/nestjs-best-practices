(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('Path', Path);

    Path.$inject = ['$resource'];

    function Path ($resource) {
        var resourceUrl =  'api/paths/:id';

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
