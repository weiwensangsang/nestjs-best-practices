/**
 * Created by OlyLis on 16-8-5.
 */
(function () {
    'use strict';

    angular
        .module('bsbAdminApp')
        .factory('PersonInfoSeriesAll', PersonInfoSeriesAll);

    PersonInfoSeriesAll.$inject = ['$resource'];

    function PersonInfoSeriesAll($resource) {
        var service = $resource('api/bsb-series/admin/get-organize', {}, {
            'query': {method: 'GET'}
        });
        return service;
    }
})();
