(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker', 'FakerCreate'];

    function FakerController(Faker, FakerCreate) {

        var vm = this;

        vm.fakers = [];
        vm.phone = 12345;
        loadAll();

        function loadAll() {
            Faker.query(function(result) {
                vm.fakers = result;
                vm.searchQuery = null;
            });
            FakerCreate.save({}, vm.phone, function onUpdateSuccess(result) {
                console.log(result.message);
            }, function onUpdateError() {
            });
        }
    }
})();
