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


        vm.goToStation = goToStation;


        function goToStation(page) { console.log(123);
            switch (page) {
                case 'data':
                    console.log(123);
                    break;
            }
        }

    }
})();
