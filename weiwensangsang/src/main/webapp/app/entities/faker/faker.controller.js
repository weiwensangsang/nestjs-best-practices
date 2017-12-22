(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker', 'FakerCreate', 'toaster', '$state'];

    function FakerController(Faker, FakerCreate, toaster, $state) {

        var vm = this;

        vm.fakers = [];
        vm.phone = 18311045471;
        vm.fakerCreate = fakerCreate;
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
                $state.go('faker', null, { reload: 'faker' });
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }
    }
})();
