(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerController', FakerController);

    FakerController.$inject = ['Faker'];

    function FakerController(Faker) {

        var vm = this;

        vm.fakers = [];

        loadAll();

        function loadAll() {
            Faker.query(function(result) {
                vm.fakers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
