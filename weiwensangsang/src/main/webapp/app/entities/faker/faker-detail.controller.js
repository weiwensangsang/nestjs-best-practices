(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDetailController', FakerDetailController);

    FakerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faker', 'LocationElectricBike'
                                    ,'ControlBike', 'toaster','$state'];

    function FakerDetailController($scope, $rootScope, $stateParams, previousState, entity, Faker, LocationElectricBike
                                    ,ControlBike, toaster, $state) {
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
            ControlBike.save({phone: vm.faker.phone, bike: bikeid},'unlock', function success(result) {
                toaster.pop('success', ' ', result.message);
                // 这里跳转到新页面
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }
    }
})();
