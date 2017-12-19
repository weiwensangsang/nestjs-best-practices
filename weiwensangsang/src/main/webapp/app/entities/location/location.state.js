(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('location', {
            parent: 'entity',
            url: '/location',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Locations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/locations.html',
                    controller: 'LocationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('location-detail', {
            parent: 'location',
            url: '/location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Location'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/location-detail.html',
                    controller: 'LocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Location', function($stateParams, Location) {
                    return Location.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'location',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('location-detail.edit', {
            parent: 'location-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-dialog.html',
                    controller: 'LocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location.new', {
            parent: 'location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-dialog.html',
                    controller: 'LocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                positionX: null,
                                positionY: null,
                                city: null,
                                eBikeNumber: null,
                                type: null,
                                state: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('location', null, { reload: 'location' });
                }, function() {
                    $state.go('location');
                });
            }]
        })
        .state('location.edit', {
            parent: 'location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-dialog.html',
                    controller: 'LocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location', null, { reload: 'location' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location.delete', {
            parent: 'location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-delete-dialog.html',
                    controller: 'LocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location', null, { reload: 'location' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
