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
        loadAll();

        function loadAll() {
            LocationBikeAll.query({position: vm.faker.state, faker: vm.faker.phone}, {}, function (result) {
                console.log(result);
                vm.locationElectricBikes = result.locationElectricBikes;
                vm.currentBike = result.bike;
                vm.weather = angular.fromJson(result.weather);
                console.log(vm.weather);
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
                $state.go('faker');
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function lock(bikeid) {
                    ControlBike.save({phone: vm.faker.phone, bike: bikeid},'lock', function success(result) {
                        toaster.pop('success', ' ', result.message);
                        // 这里跳转到新页面
                        $state.go('faker');
                    }, function error(result) {
                        toaster.pop('error', ' ', result.data.message);
                    });
                }
    }
})();
