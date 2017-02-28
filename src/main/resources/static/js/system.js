app.controller("systemController", function ($scope, $http, $consts) {
	
	//年级管理
	$scope.stages = [];
	$scope.grades = [];
	$scope.allGrades = {};
	$scope.refresh = function(){
		$scope.stages = [];
		$scope.grades = [];
		$scope.allGrades = {};
		$scope.selectStage = '';
		$scope.selectGrade = '';
		$http.get($consts.COURSEBASE + '/grades?organization=' + $consts.ORGANIZATION).success(function(response){
			angular.forEach(response.result, function(item, idx){
				var found = $scope.stages.indexOf(item.stage);
				if(found == -1){
					$scope.stages.push(item.stage);
					$scope.allGrades[item.stage] = []; 
				}
				
				if(item.name){
					$scope.allGrades[item.stage].push(item.name);
				}
		    }); 
	    });
		
	}
	
	$scope.refresh();
	
	//监测 年级段改变时对应年级也变化
	$scope.$watch('selectStage', function (newValue) {
	    $scope.grades = $scope.allGrades[newValue];
    });
	
	$scope.add = function(newStage, newGrade){
		if(!newGrade){
			newGrade == null;
		}
		$http.put($consts.COURSEBASE + '/grades', {name:newGrade, stage:newStage, organization:$consts.ORGANIZATION}).success(function (response) {
		      if(response.code == 0) {
		    	$scope.newStage = '';
		    	$scope.newGrade = '';
		        $scope.refresh();
		      } 
	     }).error(function (data) {});
	}
	$scope.remove = function(selectStage, selectGrade){
		var id;
		if(selectGrade == ''){
			selectGrade == null;
		}
		$http.get($consts.COURSEBASE + '/grades?organization=' + $consts.ORGANIZATION + 
				'&stage=' + selectStage + '&name=' + selectGrade).success(function(response){
			if(response.result.length == 1){
				id = response.result[0].id;
				$http['delete']($consts.COURSEBASE + '/grades/' + id).success(function(response){
				    if(response.code == 0){
				    	$scope.selectStage = '';
				    	$scope.selectGrade = '';
				    	$scope.refresh();
				    }
			    }); 
			}
	    }); 
		
	}
	
	//课程类别的管理
	$scope.types = [];
	$scope.refresh2 = function(){
		$scope.types = [];
		$http.get($consts.COURSEBASE + '/types?organization=' + $consts.ORGANIZATION).success(function(response){
			angular.forEach(response.result, function(item, idx){
				$scope.types.push({id:item.id, name:item.name});
		    }); 
	    }); 
	}
	
	$scope.refresh2();
	
	$scope.add2 = function(newType){
		$http.put($consts.COURSEBASE + '/types', {name:newType, organization:$consts.ORGANIZATION}).success(function (response) {
		      if(response.code == 0) {
		    	$scope.newType = '';
		        $scope.refresh2();
		      } 
	     }).error(function (data) {});
	}
	$scope.remove2 = function(id){
		$http['delete']($consts.COURSEBASE + '/types/' + id).success(function(response){
		    if(response.code == 0){
		    	$scope.selectType = '';
		    	$scope.refresh2();
		    }
	    }); 
	}
	
});