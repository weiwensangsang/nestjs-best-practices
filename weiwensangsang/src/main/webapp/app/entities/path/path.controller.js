(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('PathController', PathController);

    PathController.$inject = ['Path'];

    function PathController(Path) {

        var vm = this;

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
