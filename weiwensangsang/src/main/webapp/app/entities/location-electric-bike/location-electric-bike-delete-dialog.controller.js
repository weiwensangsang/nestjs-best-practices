(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationElectricBikeDeleteController',LocationElectricBikeDeleteController);

    LocationElectricBikeDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocationElectricBike'];

    function LocationElectricBikeDeleteController($uibModalInstance, entity, LocationElectricBike) {
        var vm = this;

        vm.locationElectricBike = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocationElectricBike.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
