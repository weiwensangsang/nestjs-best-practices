(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker', 'FakerCreate', 'toaster', '$state', 'FakerLocate'];

    function FakerController(Faker, FakerCreate, toaster, $state, FakerLocate) {

        var vm = this;

        vm.fakers = [];
        vm.phone = 18311045470;
        vm.fakerCreate = fakerCreate;
        vm.fakerLocate = fakerLocate;
        loadAll();

        function loadAll() {
            Faker.query(function(result) {
                vm.fakers = result;
                vm.searchQuery = null;
            });

        }

        function fakerCreate() {
            FakerCreate.save({}, vm.phone, function success(result) {
                toaster.pop('success', ' ', result.message);
                $state.go('faker', null, { reload: true });
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function fakerLocate() {
            FakerLocate.save(function success(result) {
                toaster.pop('success', ' ', result.message);
                $state.go('faker', null, { reload: true });
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }
    }
})();
