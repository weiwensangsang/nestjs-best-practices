/**
 * Created by lth14 on 2017/2/19.
 */
(function () {
    'use strict';

    angular
        .module('bsbAdminApp')
        .factory('SeriesAdminGetCore', SeriesAdminGetCore);

    SeriesAdminGetCore.$inject = ['$resource'];

    function SeriesAdminGetCore($resource) {
        var service = $resource('api/bsb-series/admin/get-core/:id', {}, {
            'get': {method: 'GET'}
        });
        return service;
    }
})();
