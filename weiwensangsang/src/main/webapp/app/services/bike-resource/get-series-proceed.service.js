/**
 * Created by lth on 2016/11/29.
 */
(function () {
    'use strict';

    angular
        .module('bsbAdminApp')
        .factory('GetSeriesProceed', GetSeriesProceed);

    GetSeriesProceed.$inject = ['$resource'];

    function GetSeriesProceed($resource) {
        var service = $resource('api/bsb-series/get-all-series/process', {}, {
            'query': {method: 'GET',isArray: true}
        });
        return service;
    }
})();
