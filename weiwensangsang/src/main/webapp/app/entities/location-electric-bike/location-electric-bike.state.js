(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('location-electric-bike', {
            parent: 'entity',
            url: '/location-electric-bike',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LocationElectricBikes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bikes.html',
                    controller: 'LocationElectricBikeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('location-electric-bike-detail', {
            parent: 'location-electric-bike',
            url: '/location-electric-bike/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LocationElectricBike'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bike-detail.html',
                    controller: 'LocationElectricBikeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LocationElectricBike', function($stateParams, LocationElectricBike) {
                    return LocationElectricBike.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'location-electric-bike',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('location-electric-bike-detail.edit', {
            parent: 'location-electric-bike-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bike-dialog.html',
                    controller: 'LocationElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocationElectricBike', function(LocationElectricBike) {
                            return LocationElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-electric-bike.new', {
            parent: 'location-electric-bike',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bike-dialog.html',
                    controller: 'LocationElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                state: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('location-electric-bike', null, { reload: 'location-electric-bike' });
                }, function() {
                    $state.go('location-electric-bike');
                });
            }]
        })
        .state('location-electric-bike.edit', {
            parent: 'location-electric-bike',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bike-dialog.html',
                    controller: 'LocationElectricBikeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocationElectricBike', function(LocationElectricBike) {
                            return LocationElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-electric-bike', null, { reload: 'location-electric-bike' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-electric-bike.delete', {
            parent: 'location-electric-bike',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-electric-bike/location-electric-bike-delete-dialog.html',
                    controller: 'LocationElectricBikeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocationElectricBike', function(LocationElectricBike) {
                            return LocationElectricBike.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-electric-bike', null, { reload: 'location-electric-bike' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
