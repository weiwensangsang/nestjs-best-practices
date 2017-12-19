(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationDeleteController',LocationDeleteController);

    LocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Location'];

    function LocationDeleteController($uibModalInstance, entity, Location) {
        var vm = this;

        vm.location = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Location.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
