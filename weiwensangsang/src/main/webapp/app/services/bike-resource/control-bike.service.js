(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('ControlBike', ControlBike);

    ControlBike.$inject = ['$resource'];

    function ControlBike($resource) {
        var service = $resource('api/electric-bikes/lock-control/phone/:phone/bike/:bikeid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
