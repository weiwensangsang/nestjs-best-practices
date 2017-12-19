(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sms-code', {
            parent: 'entity',
            url: '/sms-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SmsCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sms-code/sms-codes.html',
                    controller: 'SmsCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('sms-code-detail', {
            parent: 'sms-code',
            url: '/sms-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SmsCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sms-code/sms-code-detail.html',
                    controller: 'SmsCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SmsCode', function($stateParams, SmsCode) {
                    return SmsCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sms-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sms-code-detail.edit', {
            parent: 'sms-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-code/sms-code-dialog.html',
                    controller: 'SmsCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SmsCode', function(SmsCode) {
                            return SmsCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sms-code.new', {
            parent: 'sms-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-code/sms-code-dialog.html',
                    controller: 'SmsCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phone: null,
                                code: null,
                                type: null,
                                state: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sms-code', null, { reload: 'sms-code' });
                }, function() {
                    $state.go('sms-code');
                });
            }]
        })
        .state('sms-code.edit', {
            parent: 'sms-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-code/sms-code-dialog.html',
                    controller: 'SmsCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SmsCode', function(SmsCode) {
                            return SmsCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sms-code', null, { reload: 'sms-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sms-code.delete', {
            parent: 'sms-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-code/sms-code-delete-dialog.html',
                    controller: 'SmsCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SmsCode', function(SmsCode) {
                            return SmsCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sms-code', null, { reload: 'sms-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
