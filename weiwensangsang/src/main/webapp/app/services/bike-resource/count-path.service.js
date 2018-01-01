(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('CountPath', CountPath);

    CountPath.$inject = ['$resource'];

    function CountPath($resource) {
        var service = $resource('api/paths/algo-recommend/src/:src/dst/:dst', {}, {
            'query': {method: 'POST'}
        });
        return service;
    }
})();
