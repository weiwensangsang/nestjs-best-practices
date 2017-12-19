(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('PathDialogController', PathDialogController);

    PathDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Path', 'Location'];

    function PathDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Path, Location) {
        var vm = this;

        vm.path = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.path.id !== null) {
                Path.update(vm.path, onSaveSuccess, onSaveError);
            } else {
                Path.save(vm.path, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:pathUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
