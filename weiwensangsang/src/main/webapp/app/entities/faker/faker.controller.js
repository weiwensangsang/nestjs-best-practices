(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker', 'FakerCreate', 'toaster', '$state', 'FakerLocate', 'FakerDeposit'];

    function FakerController(Faker, FakerCreate, toaster, $state, FakerLocate, FakerDeposit) {

        var vm = this;

        vm.fakers = [];
        vm.phone = 18311045470;
        vm.fakerCreate = fakerCreate;
        vm.fakerLocate = fakerLocate;
        vm.action = action;
        loadAll();

        function action(phone) {
            FakerDeposit.save({phone: phone}, null, function success(result) {
                toaster.pop('success', ' ', result.message);
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
            $state.go('faker', null, {reload: true});
        }

        function loadAll() {
            Faker.query(function (result) {
                vm.fakers = result;
                vm.searchQuery = null;
            });

        }

        function fakerCreate() {
            FakerCreate.save({}, vm.phone, function success(result) {
                toaster.pop('success', ' ', result.message);
                $state.go('faker', null, {reload: true});
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function fakerLocate() {
            FakerLocate.save(function success(result) {
                toaster.pop('success', ' ', result.message);
                $state.go('faker', null, {reload: true});
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }
    }
})();
