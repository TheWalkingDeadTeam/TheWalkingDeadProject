<%--
  Created by IntelliJ IDEA.
  User: Kryvonis
  Date: 4/29/16
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="app">
<head>
    <title>StudView</title>
</head>
<body ng-controller="User">
VIEW STUDENT
<h1>Dynamic Table</h1> Actions:
<button ng-click="addUser()">Add User</button>
<table-dynamic data="tableUser"></table-dynamic>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
<script>
    /**
     * Created by Kryvonis on 4/30/16.
     */
    var mystudentslist = [];
        $.ajax({
            type        :   'GET',
            url         :   '/resources/json/studentsData.json',
            async       :   false,
            beforeSend  :   function(){/*loading*/},
            dataType    :   'json',
            success     :   function(result){

                $.each(result, function(index, val){
                    for(var i=0; i < val.length; i++){
                        var item = val[i];
                        console.log(item.name)
                        mystudentslist.add(item)
                    }
                });

            },
        });
    console.log(mystudentslist)
    var app = angular.module('app', []);

    app.controller('User', function ($scope, $timeout) {

        $scope.tableUser = null;

        // Using request to get this JSON
        $timeout(function () {
            $scope.tableUser = {
                headers: {
                    name: 'User',
                    email: 'E-mail',
                    phone: 'Phone',
                    address: 'Address'
                },
                rows: mystudentslist
//                rows: [{
//                    "name": "",
//                    "email": "orci@luctusetultrices.co.uk",
//                    "phone": "798-0149",
//                    "address": "7560 Ligula. Road"
//                }, {
//                    "name": "Brandon",
//                    "email": "",
//                    "phone": "1-280-259-7547",
//                    "address": "P.O. Box 376, 8820 At Avenue"
//                }, {
//                    "name": "Charissa",
//                    "email": "non.enim.commodo@Fusce.com",
//                    "phone": "1-870-298-7936",
//                    "address": ""
//                }, {
//                    "name": "Quinlan",
//                    "email": "magna.tellus@dolor.com",
//                    "phone": "1-537-673-1024",
//                    "address": "1654 Sem Ave"
//                }, {
//                    "name": "Nayda",
//                    "email": "orci.adipiscing@Nunc.com",
//                    "phone": "1-398-890-6994",
//                    "address": "112-9766 Tempus Avenue"
//                }, {
//                    "name": "Eugenia",
//                    "email": "lacinia.orci@rutrumjusto.com",
//                    "phone": "147-4244",
//                    "address": "625-7238 Sed Ave"
//                }, {
//                    "name": "Ora",
//                    "email": "egestas.a@Duiselementum.org",
//                    "phone": "1-873-737-1875",
//                    "address": "P.O. Box 254, 1068 Nam Street"
//                }]
            };
        }, 100);

        // Testing for Add User in Table
        $scope.addUser = function () {
            $scope.tableUser.rows.push({
                "name": "Bethany",
                "email": "orci@luctusetultrices.co.uk",
                "address": "7560 Ligula. Road"
            });
        };

    });

    app.filter('notOrder', function () {
        return function (object) {
            var array = [];
            for (var item in object) {
                if (item !== '$$hashKey') {
                    array.push(object[item]);
                }
            }
            return array;
        };
    });

    app.directive('tableDynamic', function () {
        return {
            restrict: 'E',
            scope: {
                data: '='
            },
            replace: true,
            template: '<table><thead><tr><th ng-repeat="header in data.headers | notOrder">{{header}}</th></tr></thead><tbody><tr ng-repeat="items in data.rows | filter: filterTable as count"><td ng-repeat="item in items | notOrder">{{item}}</td></tr></tbody><tfoot><tr><td colspan="{{columns.length}}">Users found: {{count.length}}</td></tr></tfoot></table>',
            compile: function compile(elem, attr) {
                return {
                    pre: function (scope, elem, attr) {
                        var buildTable = function () {
                            scope.columns = [];
                            // Detect Columns
                            angular.forEach(scope.data.headers, function (header, keyHeader) {
                                scope.columns.push(keyHeader);
                            });
                            // End Detect Columns
                            angular.forEach(scope.data.rows, function (row, keyRow) {
                                // Remove Attribute JSON no Used
                                angular.forEach(row, function (rowData, keyColumn) {
                                    if (scope.columns.indexOf(keyColumn) < 0) {
                                        delete row[keyColumn];
                                    }
                                });
                                // End Remove Attribute
                                // Complete JSON
                                angular.forEach(scope.columns, function (col) {
                                    if (row.hasOwnProperty(col) === false) {
                                        row[col] = null;
                                    }
                                });
                                // End Complete JSON
                                // Organize Row
                                var newRow = {};
                                angular.forEach(scope.columns, function (col) {
                                    newRow[col] = row[col];
                                });
                                scope.data.rows[keyRow] = newRow;
                                // End Organize Row
                            });
                        };

                        scope.$watch('data', function (newValue, oldValue) {
                            if (newValue !== oldValue) {
                                if (newValue !== null && newValue !== undefined) {
                                    buildTable();
                                }
                            }
                        }, true);

                    }
                };
            }
        };
    });
</script>

</body>
</html>
