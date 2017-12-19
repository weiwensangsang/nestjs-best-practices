(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ElectricBikeController', ElectricBikeController);

    ElectricBikeController.$inject = ['ElectricBike'];

    function ElectricBikeController(ElectricBike) {

        var vm = this;

        vm.electricBikes = [];

        loadAll();

        function loadAll() {
            ElectricBike.query(function(result) {
                vm.electricBikes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
