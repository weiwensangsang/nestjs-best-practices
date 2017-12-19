(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ElectricBikeDialogController', ElectricBikeDialogController);

    ElectricBikeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ElectricBike'];

    function ElectricBikeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ElectricBike) {
        var vm = this;

        vm.electricBike = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.electricBike.id !== null) {
                ElectricBike.update(vm.electricBike, onSaveSuccess, onSaveError);
            } else {
                ElectricBike.save(vm.electricBike, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:electricBikeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
