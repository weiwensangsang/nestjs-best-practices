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
                    break;
                case 'location':
                    $state.go('location');
                    break;
                case 'bike':
                    $state.go('location-electric-bike');
                    break;
                case 'run':
                    $state.go('path');
                    break;
            }
        }

    }
})();
