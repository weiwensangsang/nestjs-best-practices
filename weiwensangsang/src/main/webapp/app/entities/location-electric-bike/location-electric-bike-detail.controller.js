(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationElectricBikeDetailController', LocationElectricBikeDetailController);

    LocationElectricBikeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocationElectricBike', 'ElectricBike', 'Location'];

    function LocationElectricBikeDetailController($scope, $rootScope, $stateParams, previousState, entity, LocationElectricBike, ElectricBike, Location) {
        var vm = this;

        vm.locationElectricBike = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weiwensangsangApp:locationElectricBikeUpdate', function(event, result) {
            vm.locationElectricBike = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
