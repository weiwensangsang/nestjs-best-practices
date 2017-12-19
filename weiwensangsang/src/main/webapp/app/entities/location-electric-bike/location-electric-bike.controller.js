(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationElectricBikeController', LocationElectricBikeController);

    LocationElectricBikeController.$inject = ['LocationElectricBike'];

    function LocationElectricBikeController(LocationElectricBike) {

        var vm = this;

        vm.locationElectricBikes = [];

        loadAll();

        function loadAll() {
            LocationElectricBike.query(function(result) {
                vm.locationElectricBikes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
