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


        vm.goToFaker = goToFaker;


        function goToFaker(data) {
            switch (data) {
                case 'faker':
                    console.log(123);
                    $state.go('faker');
                    break;
            }
        }

    }
})();
