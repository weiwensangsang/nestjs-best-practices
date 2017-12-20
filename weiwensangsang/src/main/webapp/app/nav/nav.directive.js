(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .directive('manageNav', manageNav);

    manageNav().$inject = [];

    function manageNav () {
        var directive;
        directive = {
            restrict : 'EA',
            templateUrl : 'app/nav/nav.html',
            scope : {},
            controller : 'ManageNavController',
            controllerAs : 'vm',
            bindToController : true
        };
        return directive;
    }
})();
