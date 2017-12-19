(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LogHistoryDialogController', LogHistoryDialogController);

    LogHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LogHistory', 'Faker'];

    function LogHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LogHistory, Faker) {
        var vm = this;

        vm.logHistory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.fakers = Faker.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.logHistory.id !== null) {
                LogHistory.update(vm.logHistory, onSaveSuccess, onSaveError);
            } else {
                LogHistory.save(vm.logHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:logHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
