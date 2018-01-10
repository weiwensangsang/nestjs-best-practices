(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('FakerDeposit', FakerDeposit);

    FakerDeposit.$inject = ['$resource'];

    function FakerDeposit($resource) {
        var service = $resource('api/fakers/deposit/:phone', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
