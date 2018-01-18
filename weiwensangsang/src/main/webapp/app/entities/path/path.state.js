(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('path', {
                parent: 'entity',
                url: '/path',
                params: {
                    'dto': null
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Paths'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/path/paths.html',
                        controller: 'PathController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('path-detail', {
                parent: 'path',
                url: '/path/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Path'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/path/path-detail.html',
                        controller: 'PathDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Path', function ($stateParams, Path) {
                        return Path.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'path',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('path-detail.edit', {
                parent: 'path-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/path/path-dialog.html',
                        controller: 'PathDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Path', function (Path) {
                                return Path.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('path.new', {
                parent: 'path',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/path/path-dialog.html',
                        controller: 'PathDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    state: null,
                                    length: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('path', null, {reload: 'path'});
                    }, function () {
                        $state.go('path');
                    });
                }]
            })
            .state('path.edit', {
                parent: 'path',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/path/path-dialog.html',
                        controller: 'PathDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Path', function (Path) {
                                return Path.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('path', null, {reload: 'path'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('path.delete', {
                parent: 'path',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/path/path-delete-dialog.html',
                        controller: 'PathDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Path', function (Path) {
                                return Path.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('path', null, {reload: 'path'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
