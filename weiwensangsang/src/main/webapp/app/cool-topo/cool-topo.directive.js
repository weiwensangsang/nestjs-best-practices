(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .directive('coolTopo', coolTopo);

    coolTopo().$inject = [];

    function coolTopo () {
        var directive;
        directive = {
            restrict : 'EA',
            templateUrl : 'app/cool-topo/cool-topo.html',
            scope : {},
            controller : 'CoolTopoController',
            controllerAs : 'vm',
            bindToController : true
        };
        return directive;
    }
})();
