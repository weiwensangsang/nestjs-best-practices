(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('CountPath', CountPath);

    CountPath.$inject = ['$resource'];

    function CountPath($resource) {
        var service = $resource('api/paths/algo-recommend/src/:src/dst/:dst/type/:type', {}, {
            'query': {method: 'POST'}
        });
        return service;
    }
})();
