(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerDetailController', FakerDetailController);

    FakerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faker'];

    function FakerDetailController($scope, $rootScope, $stateParams, previousState, entity, Faker) {
        var vm = this;

        vm.faker = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weiwensangsangApp:fakerUpdate', function(event, result) {
            vm.faker = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
