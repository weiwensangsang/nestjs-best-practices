(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('SmsCodeController', SmsCodeController);

    SmsCodeController.$inject = ['SmsCode'];

    function SmsCodeController(SmsCode) {

        var vm = this;

        vm.smsCodes = [];

        loadAll();

        function loadAll() {
            SmsCode.query(function(result) {
                vm.smsCodes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
