(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .directive('faceCheck', faceCheck);

    faceCheck().$inject = [];

    function faceCheck () {
        var directive;
        directive = {
            restrict : 'EA',
            templateUrl : 'app/face-check/face-check.html',
            scope : {
                data: "="
            },
            controller : 'FaceCheckController',
            controllerAs : 'vm',
            bindToController : true
        };
        return directive;
    }
})();
