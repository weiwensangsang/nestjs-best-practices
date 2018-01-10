(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('faker', {
            parent: 'entity',
            url: '/faker',
            cache:false,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fakers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/faker/fakers.html',
                    controller: 'FakerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            },
            reload:true
        })
        .state('faker-detail', {
            parent: 'faker',
            url: '/faker/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Faker'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/faker/faker-detail.html',
                    controller: 'FakerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Faker', function($stateParams, Faker) {
                    return Faker.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'faker',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('faker-detail.edit', {
            parent: 'faker-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faker/faker-dialog.html',
                    controller: 'FakerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Faker', function(Faker) {
                            return Faker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('faker.new', {
            parent: 'faker',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faker/faker-dialog.html',
                    controller: 'FakerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phone: null,
                                name: null,
                                type: null,
                                state: null,
                                activated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('faker', null, { reload: 'faker' });
                }, function() {
                    $state.go('faker');
                });
            }]
        })
        .state('faker.edit', {
            parent: 'faker',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faker/faker-dialog.html',
                    controller: 'FakerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Faker', function(Faker) {
                            return Faker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('faker', null, { reload: 'faker' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('faker.delete', {
            parent: 'faker',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faker/faker-delete-dialog.html',
                    controller: 'FakerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Faker', function(Faker) {
                            return Faker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('faker', null, { reload: 'faker' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
