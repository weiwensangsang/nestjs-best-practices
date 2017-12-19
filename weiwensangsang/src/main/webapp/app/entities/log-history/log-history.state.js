(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('log-history', {
            parent: 'entity',
            url: '/log-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LogHistories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-history/log-histories.html',
                    controller: 'LogHistoryController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('log-history-detail', {
            parent: 'log-history',
            url: '/log-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LogHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-history/log-history-detail.html',
                    controller: 'LogHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LogHistory', function($stateParams, LogHistory) {
                    return LogHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'log-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('log-history-detail.edit', {
            parent: 'log-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-history/log-history-dialog.html',
                    controller: 'LogHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LogHistory', function(LogHistory) {
                            return LogHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('log-history.new', {
            parent: 'log-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-history/log-history-dialog.html',
                    controller: 'LogHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                state: null,
                                content: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('log-history', null, { reload: 'log-history' });
                }, function() {
                    $state.go('log-history');
                });
            }]
        })
        .state('log-history.edit', {
            parent: 'log-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-history/log-history-dialog.html',
                    controller: 'LogHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LogHistory', function(LogHistory) {
                            return LogHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('log-history', null, { reload: 'log-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('log-history.delete', {
            parent: 'log-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-history/log-history-delete-dialog.html',
                    controller: 'LogHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LogHistory', function(LogHistory) {
                            return LogHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('log-history', null, { reload: 'log-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
