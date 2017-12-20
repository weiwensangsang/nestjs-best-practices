(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', '$state', 'PersonInfo'];

    function HomeController ($rootScope, $scope, $state, PersonInfo) {
        var vm = this;

        $rootScope._homeState = true;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.show = null;
        // vm.goToManageTournament = goToManageTournament;
        // vm.goToManageStation = goToManageStation;
        // vm.goToTournamentCreate = goToTournamentCreate;
        // vm.applyStation = applyStation;
        // vm.gotoAnchor = gotoAnchor;


        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount () {
            PersonInfo.get(function success (result) {
                    vm.account = result;
                    console.log(result);
                    if (result.id === 2) {
                        $state.go('home.not-logged');
                    } else {
                        $state.go('home.first-logged');
                    }

                }, function error () {
                    $state.go('home.not-logged');
                }
            );

        }

        function goToManageTournament () {
            $state.goCrypt('tournament-management');
        }

        function goToManageStation () {
            $state.goCrypt('station-management');
        }

        function goToTournamentCreate () {
            $state.goCrypt('tournament-create');
        }



        //落地页动画
        $scope.move = function (event) {
            var e = event || window.event;
            // console.log(e.pageX + "," + e.pageY);
            $scope.pageX = e.pageX;
            $scope.pageY = e.pageY;

            var x = (e.pageX - 800) / 12;
            var y = (e.pageY - 440) / 3;

            var img1 = document.getElementsByClassName("bsba-img1")[0];
            var img2 = document.getElementsByClassName("bsba-img2")[0];
            var img3 = document.getElementsByClassName("bsba-img3")[0];

            img1.style.transform = 'translateX('+ x/2.2 +'px) translateY('+ y/4 +'px)';
            img2.style.transform = 'translateX('+ x/2 +'px) translateY('+ y/3 +'px)';
            img3.style.transform = 'translateX('+ x/2.5 +'px) translateY('+ y/6 +'px)';
        };

        function gotoAnchor(anchor) {
            var divHeight = angular.element('#'+anchor).offset().top;
            angular.element('body, html').animate({
                "scrollTop" : divHeight
            }, 500)
        }
    }
})();
