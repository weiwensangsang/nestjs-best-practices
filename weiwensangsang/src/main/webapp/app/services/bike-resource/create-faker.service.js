(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('FakerCreate', FakerCreate);

    FakerCreate.$inject = ['$resource'];

    function FakerCreate($resource) {
        var service = $resource('api/fakers/create', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
