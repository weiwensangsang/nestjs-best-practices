(function() {
    'use strict';
    angular
        .module('weiwensangsangApp')
        .factory('SmsCode', SmsCode);

    SmsCode.$inject = ['$resource'];

    function SmsCode ($resource) {
        var resourceUrl =  'api/sms-codes/:id';

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
