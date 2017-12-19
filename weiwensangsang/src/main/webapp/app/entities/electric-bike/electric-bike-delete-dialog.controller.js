(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ElectricBikeDeleteController',ElectricBikeDeleteController);

    ElectricBikeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ElectricBike'];

    function ElectricBikeDeleteController($uibModalInstance, entity, ElectricBike) {
        var vm = this;

        vm.electricBike = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ElectricBike.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
