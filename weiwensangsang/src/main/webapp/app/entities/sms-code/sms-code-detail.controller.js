(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('SmsCodeDetailController', SmsCodeDetailController);

    SmsCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SmsCode'];

    function SmsCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, SmsCode) {
        var vm = this;

        vm.smsCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weiwensangsangApp:smsCodeUpdate', function(event, result) {
            vm.smsCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
