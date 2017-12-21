(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker', 'FakerCreate'];

    function FakerController(Faker, FakerCreate) {

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
                console.log(result.message);
            }, function error() {
            });
        }
    }
})();
