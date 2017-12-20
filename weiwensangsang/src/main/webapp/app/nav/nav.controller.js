(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ManageNavController', ManageNavController);

    ManageNavController.$inject = [];

    function ManageNavController() {
        var vm = this;

        vm.showViewPageBtn = false;
        vm.showManageBtn = true;
        vm.personInfo = {};


        vm.goToStationMain = goToStationMain;


        function goToStationMain(page) {
            switch (page) {
                case 'data':
                   console.log(123);
                    break;
            }
        }

    }
})();
