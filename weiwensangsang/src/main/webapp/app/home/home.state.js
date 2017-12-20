(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('home', {
            parent : 'app',
            url : '/',
            data : {
                authorities : []
            },
            views : {
                'content@' : {
                    templateUrl : 'app/home/home.html',
                    controller : 'HomeController',
                    controllerAs : 'vm'
                }
            }
        });

        $stateProvider.state('home.first-logged', {
            url : 'control',
            data : {
                authorities : []
            },
            views : {
                'homeFirstLogged' : {
                    templateUrl : 'app/home/home-first-logged.html'
                }
            },
            resolve : {}
        });

        $stateProvider.state('home.not-logged', {
            url : '',
            data : {
                authorities : []
            },
            views : {
                'homeNotLogged' : {
                    templateUrl : 'app/home/home-not-logged.html'
                }
            },
            resolve : {}
        });
    }
})();
