(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDialogController', FakerDialogController);

    FakerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Faker'];

    function FakerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Faker) {
        var vm = this;

        vm.faker = entity;
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
            if (vm.faker.id !== null) {
                Faker.update(vm.faker, onSaveSuccess, onSaveError);
            } else {
                Faker.save(vm.faker, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:fakerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
