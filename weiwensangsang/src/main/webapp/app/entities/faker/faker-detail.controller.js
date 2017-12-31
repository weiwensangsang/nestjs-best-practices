(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDetailController', FakerDetailController);

    FakerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faker', 'LocationElectricBike'
                                    ,'ControlBike', 'toaster','$state', 'LocationBikeAll'];

    function FakerDetailController($scope, $rootScope, $stateParams, previousState, entity, Faker, LocationElectricBike
                                    ,ControlBike, toaster, $state, LocationBikeAll) {
        var vm = this;

        vm.faker = entity;
        vm.previousState = previousState.name;
        vm.locationElectricBikes = [];
        vm.currentBike = null;
        vm.weather = null;
        vm.unlock = unlock;
        vm.lock = lock;
        vm.bikeIntegrity = '损坏';
        loadAll();

        function loadAll() {
            LocationBikeAll.query({position: vm.faker.state, faker: vm.faker.phone}, {}, function (result) {
                //console.log(result);
                vm.locationElectricBikes = result.locationElectricBikes;
                vm.currentBike = result.bike;
                vm.weather = angular.fromJson(result.weather);

                vm.searchQuery = null;
                if (vm.currentBike.integrity >= 50) {
                    vm.bikeIntegrity = '可骑'
                }
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
               loadAll();
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function lock(bikeid) {
                    ControlBike.save({phone: vm.faker.phone, bike: bikeid},'lock', function success(result) {
                        toaster.pop('success', ' ', result.message);
                        // 这里跳转到新页面
                        loadAll();
                    }, function error(result) {
                        toaster.pop('error', ' ', result.data.message);
                    });
                }
    }
})();
