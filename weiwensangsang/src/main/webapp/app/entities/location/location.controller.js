(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationController', LocationController);

    LocationController.$inject = ['Location', 'toaster', 'DeleteLocation', 'GenerateLocation', '$state', '$rootScope'];

    function LocationController(Location, toaster, DeleteLocation, GenerateLocation, $state, $rootScope) {

        var vm = this;

        vm.locations = [];
        vm.action = action;


        function loadAll() {
            Location.query(function (result) {
                console.log(result)
                vm.locations = result;
            });
        }

        function resetTopo() {
            DeleteLocation.save(function success(result) {
                toaster.pop('success', ' ', result.message);
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function generateTopo() {
            var nodes = [];
            var links = [];
            for (var i = 0; i <= $rootScope.nodes.length - 1; i++) {
                var node = {};
                node.id = $rootScope.nodes[i].id;
                nodes.push(node);
            }
            for (var i = 0; i <= $rootScope.links.length - 1; i++) {
                var link = {};
                link.source = nodes[$rootScope.links[i].source.index];
                link.target = nodes[$rootScope.links[i].target.index];
                links.push(link);
            }


            GenerateLocation.save({nodes:nodes,links:links}, function success(result) {
                toaster.pop('success', ' ', result.message);
            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

        function action(data) {
            switch (data) {
                case 'reset':
                    resetTopo();
                    $state.go('home');
                    break;
                case 'save':
                    generateTopo();
                    $state.go('home');
                    break;
            }
        }

    }


})();
