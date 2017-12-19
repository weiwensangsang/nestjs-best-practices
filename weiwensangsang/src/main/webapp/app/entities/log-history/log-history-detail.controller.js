(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LogHistoryDetailController', LogHistoryDetailController);

    LogHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LogHistory', 'Faker'];

    function LogHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, LogHistory, Faker) {
        var vm = this;

        vm.logHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weiwensangsangApp:logHistoryUpdate', function(event, result) {
            vm.logHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
