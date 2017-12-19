(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('SmsCodeDeleteController',SmsCodeDeleteController);

    SmsCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'SmsCode'];

    function SmsCodeDeleteController($uibModalInstance, entity, SmsCode) {
        var vm = this;

        vm.smsCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SmsCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
