(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationElectricBikeController', LocationElectricBikeController);

    LocationElectricBikeController.$inject = ['LocationElectricBike', 'GenerateBike', 'toaster', '$state']

    function LocationElectricBikeController(LocationElectricBike, GenerateBike, toaster, $state) {

        var vm = this;

        vm.locationElectricBikes = [];
        vm.action = action;

        loadAll();

        function loadAll() {
            LocationElectricBike.query(function (result) {
                vm.locationElectricBikes = result;
                vm.searchQuery = null;
            });
        }

        function generateBike(data) {
            GenerateBike.save({}, data, function success(result) {
                toaster.pop('success', ' ', result.message);
                $state.go('location-electric-bike', null, {reload: 'location-electric-bike'});
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function action(data) {
            generateBike(data);
        }
    }
})();
