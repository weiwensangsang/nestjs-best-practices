(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('LogHistory', LogHistory);

    LogHistory.$inject = ['$resource'];

    function LogHistory ($resource) {
        var resourceUrl =  'api/log-histories/:id';

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
