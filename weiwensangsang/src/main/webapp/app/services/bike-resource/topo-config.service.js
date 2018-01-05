(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('TopoConfig', TopoConfig);

    TopoConfig.$inject = ['$resource'];

    function TopoConfig($resource) {
        var service = $resource('api/topo-infos', {}, {
            'query' : {method : 'GET'},
            'save' : {method : 'POST'}
        });
        return service;
    }
})();
