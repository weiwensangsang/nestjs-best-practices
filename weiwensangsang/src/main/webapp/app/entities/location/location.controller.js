(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('LocationController', LocationController);

    LocationController.$inject = ['Location', 'toaster', 'DeleteLocation', 'GenerateLocation', '$state'];

    function LocationController(Location, toaster, DeleteLocation, GenerateLocation, $state) {

        var vm = this;

        vm.locations = [];
        vm.action = action;



        function loadAll() {
            Location.query(function(result) {
                console.log(result)
                vm.locations = result;
            });
        }

         function deleteTopo() {
             DeleteLocation.save(function success(result) {
                  toaster.pop('success', ' ', result.message);
             }, function error(result) {
                  toaster.pop('error', ' ', result.data.message);
             });
         }

         function generateTopo(x, y) {

                      GenerateLocation.save({height: x, weight: y},{}, function success(result) {
                           toaster.pop('success', ' ', result.message);
                      }, function error(result) {
                           toaster.pop('error', ' ', result.data.message);
                      });
                  }

        function action(data) {
            switch (data) {
                 case 'cover':
                      deleteTopo();
                      $state.go('location');
                      break;
                 case '6':
                      generateTopo(6, 6);
                      $state.go('location');
                      break;
                 }
            }

        }




})();
