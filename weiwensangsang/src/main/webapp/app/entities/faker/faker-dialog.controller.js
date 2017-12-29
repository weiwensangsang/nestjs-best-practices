(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDialogController', FakerDialogController);

    FakerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Faker', 'FakerActivate', 'toaster'];

    function FakerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Faker, FakerActivate, toaster) {
        var vm = this;

        vm.faker = entity;
        vm.clear = clear;
        vm.activate = activate;
        vm.save = save;
        vm.smsCode = null;
        vm.dto = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function activate () {
            vm.dto.phone = vm.faker.phone;
            vm.dto.code = vm.smsCode;
            FakerActivate.save({}, vm.dto , function success(result) {
                toaster.pop('success', ' ', result.message);
                clear();

            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
            $state.go('faker', null, {reload: true});

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
