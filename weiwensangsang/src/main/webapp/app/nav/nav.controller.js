(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('ManageNavController', ManageNavController);

    ManageNavController.$inject = ['$state'];

    function ManageNavController($state) {
        var vm = this;

        vm.showViewPageBtn = false;
        vm.showManageBtn = true;
        vm.personInfo = {};


        vm.goToWhere = goToWhere;


        function goToWhere(data) {
            switch (data) {
                case 'faker':
                    $state.go('faker');
                    break;topology
                case 'topology':
                     $state.go('faker');
                     break;
            }
        }

    }
})();
