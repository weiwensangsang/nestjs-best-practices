(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationController', LocationController);

    LocationController.$inject = ['Location', 'toaster', 'DeleteLocation'];

    function LocationController(Location, toaster, DeleteLocation) {

        var vm = this;

        vm.locations = [];
        vm.action = action;

        loadAll();

        function loadAll() {
            Location.query(function(result) {
                vm.locations = result;
                vm.searchQuery = null;
            });
        }

         function deleteTopo() {
             DeleteLocation.save(function success(result) {
                  toaster.pop('success', ' ', result.message);
             }, function error(result) {
                  toaster.pop('error', ' ', result.data.message);
             });
         }

        function action(data) {
            switch (data) {
                 case 'cover':
                      deleteTopo();
                      break;
                 case 'location':
                      $state.go('location');
                      break;
                 }
            }
        }




})();
