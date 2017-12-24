(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('DeleteLocation', DeleteLocation);

    DeleteLocation.$inject = ['$resource'];

    function DeleteLocation($resource) {
        var service = $resource('api/locations/delete-all', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
