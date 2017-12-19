(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LogHistoryDeleteController',LogHistoryDeleteController);

    LogHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'LogHistory'];

    function LogHistoryDeleteController($uibModalInstance, entity, LogHistory) {
        var vm = this;

        vm.logHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LogHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
