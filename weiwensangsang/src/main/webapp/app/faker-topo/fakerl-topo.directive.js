(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .directive('fakerTopo', fakerTopo);

    fakerTopo().$inject = [];

    function fakerTopo () {
        var directive;
        directive = {
            restrict : 'EA',
            templateUrl : 'app/faker-topo/faker-topo.html',
            scope : {},
            controller : 'FakerTopoController',
            controllerAs : 'vm',
            bindToController : true
        };
        return directive;
    }
})();
