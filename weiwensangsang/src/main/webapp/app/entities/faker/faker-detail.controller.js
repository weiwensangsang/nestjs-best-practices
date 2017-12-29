(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDetailController', FakerDetailController);

    FakerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faker', 'LocationElectricBike'
                                    ,'ControlBike', 'toaster'];

    function FakerDetailController($scope, $rootScope, $stateParams, previousState, entity, Faker, LocationElectricBike
                                    ,ControlBike, toaster) {
        var vm = this;

        vm.faker = entity;
        vm.previousState = previousState.name;
        vm.locationElectricBikes = [];
        vm.unlock = unlock;
        loadAll();

        function loadAll() {
            LocationElectricBike.query(function (result) {
                vm.locationElectricBikes = result;
                vm.searchQuery = null;
            });
        }

        var unsubscribe = $rootScope.$on('weiwensangsangApp:fakerUpdate', function(event, result) {
            vm.faker = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function unlock(bikeid) {
            toaster.pop('success', ' ', bikeid);
        }
    }
})();
