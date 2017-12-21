/**
 * Created by lth on 2016/11/29.
 */
(function () {
    'use strict';

    angular
        .module('bsbAdminApp')
        .factory('UpdateSeriesById', UpdateSeriesById);

    UpdateSeriesById.$inject = ['$resource'];

    function UpdateSeriesById($resource) {
        var service = $resource('api/bsb-series/admin/update/:id', {}, {
            'update': {method: 'PUT'}
        });
        return service;
    }
})();
