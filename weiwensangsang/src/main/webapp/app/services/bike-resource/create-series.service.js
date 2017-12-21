/**
 * Created by lth on 2016/11/29.
 */
(function () {
    'use strict';

    angular
        .module('bsbAdminApp')
        .factory('SeriesCreate', SeriesCreate);

    SeriesCreate.$inject = ['$resource'];

    function SeriesCreate($resource) {
        var service = $resource('api/bsb-series/create', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
