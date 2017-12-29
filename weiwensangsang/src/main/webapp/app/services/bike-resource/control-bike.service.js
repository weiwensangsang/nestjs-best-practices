(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('ControlBike', ControlBike);

    ControlBike.$inject = ['$resource'];

    function ControlBike($resource) {
        var service = $resource('api/electric-bikes/lock-control/phone/:phone/bike/:bike', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
