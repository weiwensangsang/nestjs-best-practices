(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('PathController', PathController);

    PathController.$inject = ['Path', '$stateParams'];

    function PathController(Path, $stateParams) {

        var vm = this;console.log($stateParams.dto);
        vm.dto = $stateParams.dto;
        vm.paths = [];

        loadAll();

        function loadAll() {
            Path.query(function(result) {
                vm.paths = result;
                vm.searchQuery = null;
            });
        }
    }
})();
