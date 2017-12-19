(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('electric-bike', {
            parent: 'entity',
            url: '/electric-bike',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectricBikes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electric-bike/electric-bikes.html',
                    controller: 'ElectricBikeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('electric-bike-detail', {
            parent: 'electric-bike',
            url: '/electric-bike/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectricBike'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electric-bike/electric-bike-detail.html',
                    controller: 'ElectricBikeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ElectricBike', function($stateParams, ElectricBike) {
                    return ElectricBike.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'electric-bike',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('electric-bike-detail.edit', {
            parent: 'electric-bike-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electric-bike/electric-bike-dialog.html',
                    controller: 'ElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElectricBike', function(ElectricBike) {
                            return ElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('electric-bike.new', {
            parent: 'electric-bike',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electric-bike/electric-bike-dialog.html',
                    controller: 'ElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bikeOrder: null,
                                name: null,
                                oil: null,
                                distance: null,
                                type: null,
                                state: null,
                                information: null,
                                integrity: null,
                                sumPath: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('electric-bike', null, { reload: 'electric-bike' });
                }, function() {
                    $state.go('electric-bike');
                });
            }]
        })
        .state('electric-bike.edit', {
            parent: 'electric-bike',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electric-bike/electric-bike-dialog.html',
                    controller: 'ElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElectricBike', function(ElectricBike) {
                            return ElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('electric-bike', null, { reload: 'electric-bike' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('electric-bike.delete', {
            parent: 'electric-bike',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electric-bike/electric-bike-delete-dialog.html',
                    controller: 'ElectricBikeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ElectricBike', function(ElectricBike) {
                            return ElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('electric-bike', null, { reload: 'electric-bike' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
