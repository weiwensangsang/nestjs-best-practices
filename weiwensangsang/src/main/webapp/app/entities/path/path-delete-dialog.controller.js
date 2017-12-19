(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('PathDeleteController',PathDeleteController);

    PathDeleteController.$inject = ['$uibModalInstance', 'entity', 'Path'];

    function PathDeleteController($uibModalInstance, entity, Path) {
        var vm = this;

        vm.path = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Path.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
