(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('SmsCodeDialogController', SmsCodeDialogController);

    SmsCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SmsCode'];

    function SmsCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SmsCode) {
        var vm = this;

        vm.smsCode = entity;
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
            if (vm.smsCode.id !== null) {
                SmsCode.update(vm.smsCode, onSaveSuccess, onSaveError);
            } else {
                SmsCode.save(vm.smsCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weiwensangsangApp:smsCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
