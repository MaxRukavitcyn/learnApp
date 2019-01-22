'use strict';

var baseUrl = 'http://localhost:8080';

var tables = angular.module('myApp', ['ngRoute']);

/*config*/

tables.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/organization', {
            templateUrl: 'template/tableOrganizations.html',
            controller: 'TableOrganizationCtrl'
        })
        .when('/employer', {
            templateUrl: 'template/tableEmployees.html',
            controller: 'TableEmployeesCtrl'
        })
        .when('/serviceEmployer', {
            templateUrl: 'template/serviceEmployer.html',
            controller: 'EmployerCtrl'
        })
        .when('/serviceOrganization', {
            templateUrl: 'template/serviceOrganization.html',
            controller: 'OrganizationCtrl'
        })
        .otherwise({
            redirectTo: '/employer'
        });
}]);


/*controllers*/
tables.controller('TableEmployeesCtrl', ['$scope', '$http', '$location', function phoneListController($scope, $http, $location) {

    $scope.title = 'Сотрудники';
    var url = baseUrl + '/employer?limit=20';

    $http.get(url, {
        params: $location.search()
    })
        .success(function (data) {
            $scope.employees = data;
        });

    $scope.searchEmployer = function () {
        $http.get(url, {
            params: $scope.employer
        }).success(function (data) {
            $scope.employees = data;
        });
    };

    //filter
    $scope.reverse = false;
    $scope.sortNameField = undefined;

    $scope.sort = function (fieldName) {
        if ($scope.sortNameField === fieldName) {
            $scope.reverse = !$scope.reverse;
        } else {
            $scope.sortNameField = fieldName;
            $scope.reverse = false;
        }
    };

}]);

tables.controller('TableOrganizationCtrl', ['$scope', '$http', '$location', function tableOrganizationCtr($scope, $http, $location) {
    var url = baseUrl + '/organization?limit=20';

    $http.get(url, {
        params: $location.search()
    })
        .success(function (data) {
            $scope.organizations = data;
        });

    $scope.searchOrganization = function () {
        $http.get(url, {
             params: $scope.organization
        })
            .success(function (data) {
                $scope.organizations = data;
            });
    };

    //filter
    $scope.reverse = false;
    $scope.sortNameField = undefined;

    $scope.sort = function (fieldName) {
        if ($scope.sortNameField === fieldName) {
            $scope.reverse = !$scope.reverse;
        } else {
            $scope.sortNameField = fieldName;
            $scope.reverse = false;
        }
    };

}]);

tables.controller('EmployerCtrl', function ($scope, $http, AddEmployer) {

    $scope.addEmployer = function () {
        AddEmployer.AddEmployerToDB($scope.employer);
    };
    $scope.deleteEmployer = function () {
        $http.delete(baseUrl + '/employer/' + $scope.id, {}).success(function () {
            alert('сотрудник удален');
        });
    };
    // var employerPatch = $scope.employerPatch;
    $scope.patchEmployer = function () {
        $http.patch(baseUrl + '/employer', $scope.employerPatch).success(function () {
                alert('сотрудник изменен');
            }
        );
    };

}).factory('AddEmployer', ['$http', function ($http) {
    var facAddEmp = {};
    facAddEmp.AddEmployerToDB = function (employer) {
        $http.post(baseUrl + '/employer', employer).success(function () {
            alert('Сотрудник добавлен');
        }).error(function () {
            alert('Ошибка, сообщите администратору');
        });

    };
    return facAddEmp;
}]);

tables.controller('OrganizationCtrl', function ($scope, $http, AddOrganization) {
    $scope.addOrganization = function () {
        AddOrganization.AddOrganizationToDB($scope.organization);
    };

    $scope.deleteOrganization = function () {
        $http.delete(baseUrl + '/organization/' + $scope.id, {}).success(function () {
            alert('организация удалена');
        }).error(function () {
            alert('ошибка удаления организации, в организации есть сотрудники');

        });
    };
    $scope.patchOrganization = function () {
        $http.patch(baseUrl+'/organization', $scope.organizationPatch, {}).success(function () {
            alert('организация изменена');
        });
    };
}).factory('AddOrganization', ['$http', function ($http) {
    var facAddOrg = {};
    facAddOrg.AddOrganizationToDB = function (organization) {
        $http.post(baseUrl + '/organization', organization).success(function () {
            alert('Организация добавлена');
        }).error(function () {
            alert('Ошибка, сообщите администратору');
        });

    };
    return facAddOrg;
}]);

