(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('DriveBike', DriveBike);

    DriveBike.$inject = ['$resource'];

    function DriveBike($resource) {
        var service = $resource('api/electric-bikes/drive/bike/:bikeid/distance/:distanceid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
