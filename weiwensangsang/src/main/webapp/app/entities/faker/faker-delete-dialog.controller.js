(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDeleteController',FakerDeleteController);

    FakerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Faker'];

    function FakerDeleteController($uibModalInstance, entity, Faker) {
        var vm = this;

        vm.faker = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Faker.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
