(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationElectricBikeDialogController', LocationElectricBikeDialogController);

    LocationElectricBikeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocationElectricBike', 'ElectricBike', 'Location'];

    function LocationElectricBikeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocationElectricBike, ElectricBike, Location) {
        var vm = this;

        vm.locationElectricBike = entity;
        vm.clear = clear;
        vm.save = save;
        vm.electricbikes = ElectricBike.query();
        vm.locations = Location.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.locationElectricBike.id !== null) {
                LocationElectricBike.update(vm.locationElectricBike, onSaveSuccess, onSaveError);
            } else {
                LocationElectricBike.save(vm.locationElectricBike, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:locationElectricBikeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
