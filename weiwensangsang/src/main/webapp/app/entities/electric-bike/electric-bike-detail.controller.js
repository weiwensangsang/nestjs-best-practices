(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ElectricBikeDetailController', ElectricBikeDetailController);

    ElectricBikeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ElectricBike'];

    function ElectricBikeDetailController($scope, $rootScope, $stateParams, previousState, entity, ElectricBike) {
        var vm = this;

        vm.electricBike = entity;
        vm.previousState = previousState.name;

        var unsubscribe =
            $rootScope.$on('weiwensangsangApp:electricBikeUpdate', function (event, result) {
                vm.electricBike = result;
            });
        $scope.$on('$destroy', unsubscribe);
    }
})();
