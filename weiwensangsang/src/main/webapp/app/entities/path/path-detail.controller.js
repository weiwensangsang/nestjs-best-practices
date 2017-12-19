(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('PathDetailController', PathDetailController);

    PathDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Path', 'Location'];

    function PathDetailController($scope, $rootScope, $stateParams, previousState, entity, Path, Location) {
        var vm = this;

        vm.path = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weiwensangsangApp:pathUpdate', function(event, result) {
            vm.path = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
